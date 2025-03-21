package com.example.myfootballcollection.data.repository

import android.util.Log
import com.example.myfootballcollection.domain.data.api.FootballApi
import com.example.myfootballcollection.domain.data.repository.FootballRepository
import com.example.myfootballcollection.domain.model.network.Response
import com.example.myfootballcollection.domain.model.network.wrapper.TeamWrapper
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode

class FootballRepositoryImpl(
    private val footballApi: FootballApi
) : FootballRepository {
    override suspend fun getTeams(name: String): Response<TeamWrapper> {
        val httpResponse = footballApi.getTeams(name)
        if (httpResponse.status == HttpStatusCode.OK) {
            val txt = httpResponse.bodyAsText()
            Log.v("Test", txt)
            val list = httpResponse.body<Response<TeamWrapper>>()
            return list
        } else {
            throw Exception("") //Very codes and throw exceptions. Will be caught in Use Cases
        }
    }
}