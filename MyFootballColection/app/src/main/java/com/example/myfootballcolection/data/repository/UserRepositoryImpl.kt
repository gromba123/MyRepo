package com.example.myfootballcolection.data.repository

import com.example.myfootballcolection.data.dataSource.GamesCollectionDao
import com.example.myfootballcolection.data.firebase.EmailFirebaseAuthenticator
import com.example.myfootballcolection.domain.error.Error
import com.example.myfootballcolection.domain.error.Result
import com.example.myfootballcolection.domain.error.UserError
import com.example.myfootballcolection.domain.model.User
import com.example.myfootballcolection.domain.repository.UserRepository

class UserRepositoryImpl (
    private val gamesCollectionDao: GamesCollectionDao,
    private val emailFirebaseAuthenticator: EmailFirebaseAuthenticator
) : UserRepository {
    override suspend fun registerUser(
        user: User,
        password: String
    ): Result<User, Error> {
        try {
            val firebaseUser = emailFirebaseAuthenticator.signUpWithEmailPassword(
                user.email, password
            )
            if (firebaseUser == null) {
                return Result.Error(UserError.Firebase.USER_ALREADY_EXISTS)
            }
            val u = user.copy(id = firebaseUser.uid)
            gamesCollectionDao.insertUser(u)
            return Result.Success(u)
        } catch (e: Exception) {
            return Result.Error(UserError.Firebase.USER_ALREADY_EXISTS)
            TODO("Need to verify all exceptions and implement them")
        }
    }

    override suspend fun getUserById(id: String): Result<User, Error> {
        TODO("Not yet implemented")
    }

    override fun logoutUser() {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser() {
        TODO("Not yet implemented")
    }
}