package com.example.myfootballcollectionkmp.controllers

import com.example.myfootballcollectionkmp.database.repository.UserRepository
import com.example.myfootballcollectionkmp.domain.User
import org.springframework.web.bind.annotation.*


//TODO("Implement filter for exception handling")
@RestController
@RequestMapping("${DEFAULT_ROUTE}${USER_ROUTES}")
class UserController(
    val userRepository: UserRepository
) {
    @PutMapping
    @RequestMapping(CREATE_USER_ROUTE)
    fun createUser(
        @RequestBody user: User
    ): Boolean {
        userRepository.createUser(user)
        return true
    }

    // returns the user info
    @GetMapping("/{user_id}")
    fun getUser(
        @PathVariable("user_id") userId: String
    ): User? = userRepository.getUser(userId)
}