package com.example.myfootballcollection.data.repository

import com.example.myfootballcollection.domain.data.repository.UserRepository
import com.example.myfootballcollection.domain.error.Error
import com.example.myfootballcollection.domain.error.Result
import com.example.myfootballcollection.domain.model.user.User

class FakeUserRepository(

) : UserRepository {
    override suspend fun loginUser(mail: String, password: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun registerUser(mail: String, password: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUser(): User {
        TODO("Not yet implemented")
    }

    suspend fun getUserById(id: String): Result<User, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User): User {
        TODO("Not yet implemented")
    }

    override suspend fun changePassword(newPassword: String) {
        TODO("Not yet implemented")
    }

    override fun logoutUser() {
        TODO("Not yet implemented")
    }
}