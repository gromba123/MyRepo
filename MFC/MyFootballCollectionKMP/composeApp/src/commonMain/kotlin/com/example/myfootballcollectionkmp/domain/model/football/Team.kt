package com.example.myfootballcollectionkmp.domain.model.football

import kotlinx.serialization.Serializable

@Serializable
data class Team(
    val id: Int,
    val name: String,
    val code: String? = null,
    val country: String? = null,
    val founded: Int? = null,
    val national: Boolean,
    val logo: String
)