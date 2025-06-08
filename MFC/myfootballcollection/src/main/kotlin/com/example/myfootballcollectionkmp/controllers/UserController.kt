package com.example.myfootballcollectionkmp.controllers

import com.example.myfootballcollectionkmp.database.repository.UserRepository
import com.example.myfootballcollectionkmp.domain.User
import com.example.myfootballcollectionkmp.domain.UserRegistration
import com.example.myfootballcollectionkmp.domain.UserUpdate
import org.springframework.web.bind.annotation.*


//TODO("Implement filter for exception handling")
@RestController
@RequestMapping("${DEFAULT_ROUTE}${USER_ROUTES}")
class UserController(
    val userRepository: UserRepository
) {
    @PutMapping(CREATE_USER_ROUTE)
    fun createUser(
        @RequestBody userRegistration: UserRegistration
    ): Boolean {
        userRepository.createUser(userRegistration)
        return true
    }

    @PostMapping(UPDATE_USER_ROUTE)
    fun updateUser(
        @PathVariable("userId") userId: String,
        @RequestBody userUpdate: UserUpdate
    ): Boolean {
        userRepository.updateUser(userUpdate)
        return true
    }

    // returns the user info
    @GetMapping("/{userId}")
    fun getUser(
        @PathVariable("userId") userId: String
    ): User? = userRepository.getUser(userId)
}