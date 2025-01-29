package com.example.myfootballcollection.data.repository

import com.example.myfootballcollection.domain.data.api.FootballApi
import com.example.myfootballcollection.domain.data.repository.FootballRepository
import com.example.myfootballcollection.domain.model.Team
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class FootballRepositoryImpl(
    private val footballApi: FootballApi
) : FootballRepository {
    override suspend fun getTeams(name: String): List<Team> {
        val httpResponse = footballApi.getTeams(name)
        if (httpResponse.status == HttpStatusCode.OK) {
            val list = httpResponse.body<List<Team>>()
            return list
        } else {
            throw Exception("") //Very codes and throw exceptions. Will be caught in Use Cases
        }
    }
}