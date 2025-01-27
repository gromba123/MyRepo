package com.example.myfootballcollection.domain.useCase.user

import com.example.myfootballcollection.domain.repository.UserRepository

class GetCurrentUser(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.getCurrentUser()
}