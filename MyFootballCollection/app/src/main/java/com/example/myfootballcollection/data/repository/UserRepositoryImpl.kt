package com.example.myfootballcollection.data.repository

import com.example.myfootballcollection.data.dataSource.GamesCollectionDao
import com.example.myfootballcollection.domain.data.firebase.FirebaseEmailAuthenticator
import com.example.myfootballcollection.domain.data.repository.UserRepository
import com.example.myfootballcollection.domain.model.User

class UserRepositoryImpl (
    private val gamesCollectionDao: GamesCollectionDao,
    private val firebaseEmailAuthenticator: FirebaseEmailAuthenticator
) : UserRepository {
    override suspend fun loginUser(
        mail: String,
        password: String
    ): User {
        val firebaseUser = firebaseEmailAuthenticator.signInWithEmailPassword(
            mail,
            password
        )
        if (firebaseUser == null) {
            throw Exception("User not found")
        }
        return getUserById(firebaseUser.uid)
    }

    override suspend fun registerUser(
        mail: String,
        password: String
    ): User {
        val firebaseUser = firebaseEmailAuthenticator.signUpWithEmailPassword(
            mail,
            password
        )
        if (firebaseUser == null) {
            throw Exception("User not found")
        }
        val u = User.buildBlankUser(firebaseUser.uid, mail)
        gamesCollectionDao.upsertUser(u)
        return u
    }

    override suspend fun getCurrentUser(): User {
        val firebaseUser = firebaseEmailAuthenticator.getCurrentUser() ?: throw Exception("User not found")
        return getUserById(firebaseUser.uid)
    }

    private suspend fun getUserById(id: String): User {
        return gamesCollectionDao.getUser(id) ?: throw Exception("User not found")
    }

    override suspend fun updateUser(user: User): User {
        gamesCollectionDao.upsertUser(user)
        return user
    }

    override suspend fun changePassword(newPassword: String) {
        TODO("Not yet implemented")
    }

    override fun logoutUser() {
        TODO("Not yet implemented")
    }
}