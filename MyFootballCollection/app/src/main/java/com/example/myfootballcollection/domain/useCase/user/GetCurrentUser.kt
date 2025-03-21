package com.example.myfootballcollection.domain.useCase.user

import com.example.myfootballcollection.domain.data.repository.UserRepository
import com.example.myfootballcollection.domain.error.Error
import com.example.myfootballcollection.domain.error.INVALID_EMAIL_CODE
import com.example.myfootballcollection.domain.error.Result
import com.example.myfootballcollection.domain.error.USER_DISABLED_CODE
import com.example.myfootballcollection.domain.error.USER_NOT_FOUND_CODE
import com.example.myfootballcollection.domain.error.UserError
import com.example.myfootballcollection.domain.error.WRONG_PASSWORD_CODE
import com.example.myfootballcollection.domain.model.user.User
import com.google.firebase.auth.FirebaseAuthException

class GetCurrentUser(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<User, Error> {
        try {
            return Result.Success(userRepository.getCurrentUser())
        } catch (e: FirebaseAuthException) {
            val error = when (e.errorCode) {
                WRONG_PASSWORD_CODE -> UserError.Firebase.Login.WRONG_PASSWORD
                INVALID_EMAIL_CODE -> UserError.Firebase.Login.INVALID_EMAIL
                USER_DISABLED_CODE -> UserError.Firebase.Login.USER_DISABLED
                USER_NOT_FOUND_CODE -> UserError.Firebase.Login.USER_NOT_FOUND
                else -> UserError.Firebase.Other.UNEXPECTED_ERROR
            }
            return Result.Error(error)
        } catch (e: Exception) {
            return Result.Error(UserError.Firebase.Other.UNEXPECTED_ERROR)
        }
    }
}