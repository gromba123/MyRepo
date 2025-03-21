package com.example.myfootballcollection.domain.model.network

import kotlinx.serialization.Serializable

@Serializable
class Response<T>(
    val response: List<T>
)