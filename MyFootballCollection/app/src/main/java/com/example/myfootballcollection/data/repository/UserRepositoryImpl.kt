package com.example.myfootballcollection.data.repository

import com.example.myfootballcollection.data.dataSource.GamesCollectionDao
import com.example.myfootballcollection.domain.error.Error
import com.example.myfootballcollection.domain.error.INVALID_EMAIL_CODE
import com.example.myfootballcollection.domain.error.OPERATION_NOT_ALLOWED_CODE
import com.example.myfootballcollection.domain.error.Result
import com.example.myfootballcollection.domain.error.USER_ALREADY_EXISTS_CODE
import com.example.myfootballcollection.domain.error.USER_DISABLED_CODE
import com.example.myfootballcollection.domain.error.USER_NOT_FOUND_CODE
import com.example.myfootballcollection.domain.error.UserError
import com.example.myfootballcollection.domain.error.WEAK_PASSWORD_CODE
import com.example.myfootballcollection.domain.error.WRONG_PASSWORD_CODE
import com.example.myfootballcollection.domain.firebase.FirebaseEmailAuthenticator
import com.example.myfootballcollection.domain.model.User
import com.example.myfootballcollection.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuthException

class UserRepositoryImpl (
    private val gamesCollectionDao: GamesCollectionDao,
    private val firebaseEmailAuthenticator: FirebaseEmailAuthenticator
) : UserRepository {
    override suspend fun loginUser(
        mail: String,
        password: String
    ): Result<User, Error> {
        try {
            val firebaseUser = firebaseEmailAuthenticator.signInWithEmailPassword(
                mail,
                password
            )
            if (firebaseUser == null) {
                return Result.Error(UserError.Firebase.Login.USER_NOT_FOUND)
            }
            return getUserById(firebaseUser.uid)
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

    override suspend fun registerUser(
        mail: String,
        password: String
    ): Result<User, Error> {
        try {
            val firebaseUser = firebaseEmailAuthenticator.signUpWithEmailPassword(
                mail,
                password
            )
            if (firebaseUser == null) {
                return Result.Error(UserError.Firebase.Create.USER_ALREADY_EXISTS)
            }
            val u = User.buildBlankUser(firebaseUser.uid, mail)
            gamesCollectionDao.upsertUser(u)
            return Result.Success(u)
        } catch (e: FirebaseAuthException) {
            val error = when (e.errorCode) {
                USER_ALREADY_EXISTS_CODE -> UserError.Firebase.Create.USER_ALREADY_EXISTS
                INVALID_EMAIL_CODE -> UserError.Firebase.Create.INVALID_EMAIL
                OPERATION_NOT_ALLOWED_CODE -> UserError.Firebase.Create.OPERATION_NOT_ALLOWED
                WEAK_PASSWORD_CODE -> UserError.Firebase.Create.WEAK_PASSWORD
                else -> UserError.Firebase.Other.UNEXPECTED_ERROR
            }
            return Result.Error(error)
        } catch (e: Exception) {
            return Result.Error(UserError.Firebase.Other.UNEXPECTED_ERROR)
        }
    }

    override suspend fun getUserById(id: String): Result<User, Error> {
        try {
            val user = gamesCollectionDao.getUser(id)
                ?: return Result.Error(UserError.Firebase.Login.USER_NOT_FOUND)
            return Result.Success(user)
        } catch (e: Exception) {
            return Result.Error(UserError.Firebase.Login.USER_NOT_FOUND)
            TODO("Need to verify all exceptions and implement them")
        }
    }

    override suspend fun updateUser(user: User): Result<User, Error> {
        try {
            gamesCollectionDao.upsertUser(user)
            return Result.Success(user)
        } catch (e: Exception) {
            return Result.Error(UserError.InvalidFields.INVALID_EMAIL)
            TODO("Need to verify all exceptions and implement them")
        }
    }

    override suspend fun changePassword(newPassword: String) {
        TODO("Not yet implemented")
    }

    override fun logoutUser() {
        TODO("Not yet implemented")
    }
}