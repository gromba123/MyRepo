package com.example.myfootballcollectionkmp.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

actual val networkModule = module {
    single<HttpClientEngine> { OkHttp.create() }
}