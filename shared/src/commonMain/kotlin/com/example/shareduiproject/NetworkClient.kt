package com.example.shareduiproject

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


expect fun getHttpEngine() : HttpClientEngine

fun createHttpClient(engine: HttpClientEngine = getHttpEngine()) : HttpClient {
    return HttpClient(engine) {
        defaultRequest {
            url("https://api.escuelajs.co/api/v1/")
        }

        install(ContentNegotiation){
            json( Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = false
            })
        }

        install(Logging){
            level = LogLevel.INFO
        }
    }
}