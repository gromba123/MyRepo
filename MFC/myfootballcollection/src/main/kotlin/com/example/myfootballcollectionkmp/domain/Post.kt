package com.example.myfootballcollectionkmp.domain

data class Post(
    val id: String,
    val path: String,
    val title: String,
    val description: String,
    val team: String,
    val season: String
)