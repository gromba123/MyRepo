package com.example.myfootballcollectionkmp.domain

//Complete user info
data class User(
    val userId: String,
    val mail: String,
    val name: String,
    val tag: String,
    val birthday: String,
    val country: String,
    val profilePictureUrl: String,
    val headerPictureUrl: String,
    val followingTeams: List<String>
)

//Basic information for db registration
data class UserRegistration(
    val userId: String,
    val mail: String
)

//Information needed to update the user profile
data class UserUpdate(
    val userId: String,
    val name: String,
    val tag: String,
    val birthday: String,
    val country: String,
    val profilePictureUrl: String,
    val headerPictureUrl: String,
    val followingTeams: List<String>
)