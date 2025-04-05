package com.example.myfootballcollectionkmp.domain.data.api

import io.ktor.client.statement.HttpResponse

interface FootballApi {
    suspend fun getTeams(name: String): HttpResponse
}