package com.example.myfootballcollection.domain.data.api

import io.ktor.client.statement.HttpResponse

interface FootballApi {
    suspend fun getTeams(name: String): HttpResponse
}