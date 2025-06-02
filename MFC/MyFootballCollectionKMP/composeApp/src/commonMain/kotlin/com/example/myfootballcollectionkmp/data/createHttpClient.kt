package com.example.myfootballcollectionkmp.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

const val TIME_OUT = 10000L

fun createHttpClient(engine: HttpClientEngine): HttpClient {
    return HttpClient(engine){

        //Logging
        install(Logging) {
            level = LogLevel.ALL
        }

        install(HttpTimeout){
            connectTimeoutMillis = TIME_OUT
            socketTimeoutMillis = TIME_OUT
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
            })
        }

        // Headers
        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
}