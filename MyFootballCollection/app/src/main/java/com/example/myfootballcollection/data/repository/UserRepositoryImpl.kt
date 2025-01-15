package com.example.myfootballcollection.data.repository

import com.example.myfootballcollection.data.dataSource.GamesCollectionDao
import com.example.myfootballcollection.data.firebase.FirebaseEmailAuthenticatorImpl
import com.example.myfootballcollection.domain.error.Error
import com.example.myfootballcollection.domain.error.Result
import com.example.myfootballcollection.domain.error.UserError
import com.example.myfootballcollection.domain.model.User
import com.example.myfootballcollection.domain.repository.UserRepository

class UserRepositoryImpl (
    private val gamesCollectionDao: GamesCollectionDao,
    private val firebaseEmailAuthenticatorImpl: FirebaseEmailAuthenticatorImpl
) : UserRepository {
    override suspend fun loginUser(
        mail: String,
        password: String
    ): Result<User, Error> {
        try {
            val firebaseUser = firebaseEmailAuthenticatorImpl.signInWithEmailPassword(
                mail,
                password
            )
            if (firebaseUser == null) {
                return Result.Error(UserError.Firebase.USER_NOT_FOUND)
            }
            return getUserById(firebaseUser.uid)
        } catch (e: Exception) {
            return Result.Error(UserError.Firebase.USER_NOT_FOUND)
            TODO("Need to verify all exceptions and implement them")
        }
    }

    override suspend fun registerUser(
        mail: String,
        password: String
    ): Result<User, Error> {
        try {
            val firebaseUser = firebaseEmailAuthenticatorImpl.signUpWithEmailPassword(
                mail,
                password
            )
            if (firebaseUser == null) {
                return Result.Error(UserError.Firebase.USER_ALREADY_EXISTS)
            }
            val u = User.buildBlankUser(firebaseUser.uid, mail)
            gamesCollectionDao.upsertUser(u)
            return Result.Success(u)
        } catch (e: Exception) {
            return Result.Error(UserError.Firebase.USER_ALREADY_EXISTS)
            TODO("Need to verify all exceptions and implement them. Also previous exceptions")
        }
    }

    override suspend fun getUserById(id: String): Result<User, Error> {
        try {
            val user = gamesCollectionDao.getUser(id)
                ?: return Result.Error(UserError.Firebase.USER_NOT_FOUND)
            return Result.Success(user)
        } catch (e: Exception) {
            return Result.Error(UserError.Firebase.USER_NOT_FOUND)
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

    override fun logoutUser() {
        TODO("Not yet implemented")
    }
}