package com.example.shareduiproject

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine


expect fun getHttpEngine() : HttpClientEngine

fun createHttpClient(engine: HttpClientEngine = getHttpEngine()) : HttpClient {
    return HttpClient(engine)
}