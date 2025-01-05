package com.example.myfootballcolection.domain.useCase.user

import com.example.myfootballcolection.domain.error.Error
import com.example.myfootballcolection.domain.error.Result
import com.example.myfootballcolection.domain.model.User
import com.example.myfootballcolection.domain.repository.UserRepository

class RegisterUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        mail: String,
        password: String,
    ): Result<User, Error> {
        //TODO("Validate fields")
        return userRepository.registerUser(mail, password)
    }
}