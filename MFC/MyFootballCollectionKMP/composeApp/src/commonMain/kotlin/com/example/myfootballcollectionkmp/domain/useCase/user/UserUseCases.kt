package com.example.myfootballcollectionkmp.domain.useCase.user

data class UserUseCases(
    val createUser: CreateUser,
    val loginUser: LoginUser,
    val getCurrentUser: GetCurrentUser,
    val isFirebaseLoggedIn: IsFirebaseLoggedIn
)