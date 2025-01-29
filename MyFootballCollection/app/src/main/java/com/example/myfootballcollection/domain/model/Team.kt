package com.example.myfootballcollection.domain.model

data class Team(
    val id: Int,
    val name: String,
    val code: String,
    val country: String,
    val founded: Int,
    val national: Boolean,
    val logo: String
)