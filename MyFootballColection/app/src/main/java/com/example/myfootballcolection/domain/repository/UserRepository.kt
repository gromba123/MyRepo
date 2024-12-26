package com.example.myfootballcolection.domain.repository

import com.example.myfootballcolection.domain.error.Error
import com.example.myfootballcolection.domain.error.Result
import com.example.myfootballcolection.domain.model.User

interface UserRepository {
    suspend fun registerUser(user: User, password: String): Result<User, Error>
    suspend fun getUserById(id: String): Result<User, Error>
    suspend fun updateUser()
    fun logoutUser()
}