package com.example.myfootballcollectionkmp.domain

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