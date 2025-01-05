package com.example.myfootballcolection.domain.repository

import com.example.myfootballcolection.domain.error.Error
import com.example.myfootballcolection.domain.error.Result
import com.example.myfootballcolection.domain.model.User

interface UserRepository {
    suspend fun loginUser(mail: String, password: String): Result<User, Error>
    suspend fun registerUser(mail: String, password: String): Result<User, Error>
    suspend fun getUserById(id: String): Result<User, Error>
    suspend fun updateUser(user: User): Result<User, Error>
    fun logoutUser()
}