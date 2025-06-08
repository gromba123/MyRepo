package com.example.myfootballcollectionkmp.data.repository

import com.example.myfootballcollectionkmp.domain.data.api.FootballApi
import com.example.myfootballcollectionkmp.domain.data.repository.FootballRepository
import com.example.myfootballcollectionkmp.domain.model.network.FootballApiResponse
import com.example.myfootballcollectionkmp.domain.model.network.wrapper.FootballApiTeam
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class FootballRepositoryImpl(
    private val footballApi: FootballApi
) : FootballRepository {
    override suspend fun getTeams(name: String): FootballApiResponse<FootballApiTeam> {
        val httpResponse = footballApi.getTeams(name)
        if (httpResponse.status == HttpStatusCode.OK) {
            val list = httpResponse.body<FootballApiResponse<FootballApiTeam>>()
            return list
        } else {
            throw Exception("") //Very codes and throw exceptions. Will be caught in Use Cases
        }
    }
}