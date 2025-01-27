package com.example.myfootballcollection.domain.useCase.user

data class UserUseCases(
    val registerUser: RegisterUser,
    val loginUser: LoginUser,
    val getCurrentUser: GetCurrentUser,
    val isFirebaseLoggedIn: IsFirebaseLoggedIn
)