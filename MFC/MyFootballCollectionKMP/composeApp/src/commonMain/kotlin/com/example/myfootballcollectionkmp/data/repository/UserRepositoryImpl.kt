package com.example.myfootballcollectionkmp.data.repository

import com.example.myfootballcollectionkmp.domain.data.api.UserApi
import com.example.myfootballcollectionkmp.domain.data.firebase.FirebaseEmailAuthenticator
import com.example.myfootballcollectionkmp.domain.data.repository.UserRepository
import com.example.myfootballcollectionkmp.domain.model.user.User
import com.example.myfootballcollectionkmp.domain.model.user.UserRegistration
import com.example.myfootballcollectionkmp.domain.model.user.UserUpdate
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class UserRepositoryImpl (
    private val firebaseEmailAuthenticator: FirebaseEmailAuthenticator,
    private val userApi: UserApi
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

    override suspend fun createUser(
        mail: String,
        password: String
    ): Boolean {
        val firebaseUser = firebaseEmailAuthenticator.signUpWithEmailPassword(
            mail,
            password
        )
        if (firebaseUser == null) {
            throw Exception("User not found")
        }
        val httpResponse = userApi.createUser(
            UserRegistration(firebaseUser.uid, mail)
        )
        if (httpResponse.status == HttpStatusCode.OK) {
            return httpResponse.body<Boolean>()
        } else {
            throw Exception("") //Very codes and throw exceptions. Will be caught in Use Cases
        }
    }

    override suspend fun getUser(): User {
        val firebaseUser = firebaseEmailAuthenticator.getCurrentUser() ?: throw Exception("User not found")
        return getUserById(firebaseUser.uid)
    }

    private suspend fun getUserById(id: String): User {
        val httpResponse = userApi.getUser(id)
        if (httpResponse.status == HttpStatusCode.OK) {
            return httpResponse.body<User>()
        } else {
            throw Exception("") //Very codes and throw exceptions. Will be caught in Use Cases
        }
    }

    override suspend fun updateUser(
        name: String,
        tag: String,
        birthday: String,
        country: String,
        profilePictureUrl: String,
        headerPictureUrl: String,
        followingTeams: List<String>
    ): Boolean {
        val firebaseUser = firebaseEmailAuthenticator.getCurrentUser() ?: throw Exception("User not found")

        val userUpdate = UserUpdate(
            firebaseUser.uid,
            name,
            tag,
            birthday,
            country,
            profilePictureUrl,
            headerPictureUrl,
            followingTeams
        )

        val httpResponse = userApi.updateUser(userUpdate)
        if (httpResponse.status == HttpStatusCode.OK) {
            return httpResponse.body<Boolean>()
        } else {
            throw Exception("") //Very codes and throw exceptions. Will be caught in Use Cases
        }
    }

    override suspend fun changePassword(newPassword: String) {
        TODO("Not yet implemented")
    }

    override fun logoutUser() {
        TODO("Not yet implemented")
    }
}