package com.example.shareduiproject

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine


expect fun getHttpEngine() : HttpClientEngine

fun createHttpClient() : HttpClient {
    return HttpClient(getHttpEngine())
}