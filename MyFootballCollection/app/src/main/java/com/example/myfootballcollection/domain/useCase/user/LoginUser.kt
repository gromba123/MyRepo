package com.example.myfootballcollection.domain.useCase.user

import com.example.myfootballcollection.domain.error.Error
import com.example.myfootballcollection.domain.error.Result
import com.example.myfootballcollection.domain.model.User
import com.example.myfootballcollection.domain.repository.UserRepository

class LoginUser(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        mail: String,
        password: String,
    ): Result<User, Error> {
        //TODO("Validate fields")
        return userRepository.loginUser(mail, password)
    }
}