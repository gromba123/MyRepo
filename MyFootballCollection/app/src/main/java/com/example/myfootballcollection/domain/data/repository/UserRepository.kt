package com.example.myfootballcollection.domain.data.repository

import com.example.myfootballcollection.domain.model.user.User

interface UserRepository {
    suspend fun loginUser(mail: String, password: String): User
    suspend fun registerUser(mail: String, password: String): User
    suspend fun getCurrentUser(): User
    suspend fun updateUser(user: User): User
    suspend fun changePassword(newPassword: String)
    fun logoutUser()
}