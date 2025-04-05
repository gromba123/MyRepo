package com.example.myfootballcollectionkmp.domain.useCase.user

import com.example.myfootballcollectionkmp.domain.data.repository.UserRepository
import com.example.myfootballcollectionkmp.domain.error.Error
import com.example.myfootballcollectionkmp.domain.error.INVALID_EMAIL_CODE
import com.example.myfootballcollectionkmp.domain.error.OPERATION_NOT_ALLOWED_CODE
import com.example.myfootballcollectionkmp.domain.error.Result
import com.example.myfootballcollectionkmp.domain.error.USER_ALREADY_EXISTS_CODE
import com.example.myfootballcollectionkmp.domain.error.UserError
import com.example.myfootballcollectionkmp.domain.error.WEAK_PASSWORD_CODE
import com.example.myfootballcollectionkmp.domain.model.user.User
import dev.gitlive.firebase.auth.FirebaseAuthException

class RegisterUser(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        mail: String,
        password: String,
    ): Result<User, Error> {
        try {
            //TODO("Validate fields")
            return Result.Success(userRepository.registerUser(mail, password))
        } catch (e: FirebaseAuthException) {
            val error = when (e.message) {
                USER_ALREADY_EXISTS_CODE -> UserError.Firebase.Register.USER_ALREADY_EXISTS
                INVALID_EMAIL_CODE -> UserError.Firebase.Register.INVALID_EMAIL
                OPERATION_NOT_ALLOWED_CODE -> UserError.Firebase.Register.OPERATION_NOT_ALLOWED
                WEAK_PASSWORD_CODE -> UserError.Firebase.Register.WEAK_PASSWORD
                else -> UserError.Firebase.Other.UNEXPECTED_ERROR
            }
            return Result.Error(error)
        } catch (e: Exception) {
            return Result.Error(UserError.Firebase.Other.UNEXPECTED_ERROR)
        }
    }
}