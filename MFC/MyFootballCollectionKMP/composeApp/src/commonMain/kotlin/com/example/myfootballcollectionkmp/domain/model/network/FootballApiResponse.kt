package com.example.myfootballcollectionkmp.domain.model.network

import kotlinx.serialization.Serializable

@Serializable
class FootballApiResponse<T>(
    val response: List<T>
)