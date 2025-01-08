package com.example.myfootballcolection.data.repository

import com.example.myfootballcolection.domain.error.Error
import com.example.myfootballcolection.domain.error.Result
import com.example.myfootballcolection.domain.model.User
import com.example.myfootballcolection.domain.repository.UserRepository

class FakeUserRepository(

) : UserRepository {
    override suspend fun loginUser(mail: String, password: String): Result<User, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun registerUser(mail: String, password: String): Result<User, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(id: String): Result<User, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User): Result<User, Error> {
        TODO("Not yet implemented")
    }

    override fun logoutUser() {
        TODO("Not yet implemented")
    }
}