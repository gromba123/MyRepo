package com.example.myfootballcollectionkmp.data.repository

import com.example.myfootballcollectionkmp.domain.data.api.FootballApi
import com.example.myfootballcollectionkmp.domain.data.repository.FootballRepository
import com.example.myfootballcollectionkmp.domain.model.network.Response
import com.example.myfootballcollectionkmp.domain.model.network.wrapper.TeamWrapper
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class FootballRepositoryImpl(
    private val footballApi: FootballApi
) : FootballRepository {
    override suspend fun getTeams(name: String): Response<TeamWrapper> {
        val httpResponse = footballApi.getTeams(name)
        if (httpResponse.status == HttpStatusCode.OK) {
            val list = httpResponse.body<Response<TeamWrapper>>()
            return list
        } else {
            throw Exception("") //Very codes and throw exceptions. Will be caught in Use Cases
        }
    }
}