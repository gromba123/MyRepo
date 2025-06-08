package com.example.myfootballcollectionkmp.data.api

import com.example.myfootballcollectionkmp.domain.data.api.UserApi
import com.example.myfootballcollectionkmp.domain.model.user.UserRegistration
import com.example.myfootballcollectionkmp.domain.model.user.UserUpdate
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class UserApiImpl(
    private val httpClient: HttpClient
) : UserApi {
    override suspend fun createUser(userRegistration: UserRegistration): HttpResponse {
        return httpClient.put(Route.User.Create.getRoute()) {
            setBody(userRegistration)
        }
    }

    override suspend fun updateUser(userUpdate: UserUpdate): HttpResponse {
        return httpClient.post(Route.User.Update.getRoute()) {
            setBody(userUpdate)
        }
    }

    override suspend fun getUser(userId: String): HttpResponse {
        return httpClient.get(Route.User.Get.getRoute(userId))
    }
}