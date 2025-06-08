package com.example.myfootballcollectionkmp.domain.data.repository

import com.example.myfootballcollectionkmp.domain.model.user.User

interface UserRepository {
    suspend fun loginUser(mail: String, password: String): User
    suspend fun createUser(mail: String, password: String): Boolean
    suspend fun getUser(): User
    suspend fun updateUser(
        name: String,
        tag: String,
        birthday: String,
        country: String,
        profilePictureUrl: String,
        headerPictureUrl: String,
        followingTeams: List<String>
    ): Boolean
    suspend fun changePassword(newPassword: String)
    fun logoutUser()
}