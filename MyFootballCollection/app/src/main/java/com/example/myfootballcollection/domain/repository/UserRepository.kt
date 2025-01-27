package com.example.myfootballcollection.domain.repository

import com.example.myfootballcollection.domain.error.Error
import com.example.myfootballcollection.domain.error.Result
import com.example.myfootballcollection.domain.model.User

interface UserRepository {
    suspend fun loginUser(mail: String, password: String): Result<User, Error>
    suspend fun registerUser(mail: String, password: String): Result<User, Error>
    suspend fun getCurrentUser(): Result<User, Error>
    suspend fun updateUser(user: User): Result<User, Error>
    suspend fun changePassword(newPassword: String)
    fun logoutUser()
}