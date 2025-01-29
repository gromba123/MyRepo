package com.example.myfootballcollection.data.api

import com.example.myfootballcollection.domain.data.api.FootballApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class FootballApiImpl(
    private val httpClient: HttpClient
) : FootballApi {
    override suspend fun getTeams(name: String): HttpResponse {
        httpClient.get(Route.Football.SearchTeams.getRoute(name)) {
            headers[SPORTS_API_HEADER] = SPORTS_API_KEY
        }
        TODO("Not yet implemented")
    }
}