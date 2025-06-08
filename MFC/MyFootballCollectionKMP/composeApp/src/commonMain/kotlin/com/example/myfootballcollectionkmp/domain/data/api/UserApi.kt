package com.example.myfootballcollectionkmp.domain.data.api

import com.example.myfootballcollectionkmp.domain.model.user.UserRegistration
import com.example.myfootballcollectionkmp.domain.model.user.UserUpdate
import io.ktor.client.statement.HttpResponse

interface UserApi {
    suspend fun createUser(userRegistration: UserRegistration): HttpResponse
    suspend fun updateUser(userUpdate: UserUpdate): HttpResponse
    suspend fun getUser(userId: String): HttpResponse
}