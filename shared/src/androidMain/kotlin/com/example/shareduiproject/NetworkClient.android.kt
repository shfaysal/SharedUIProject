package com.example.shareduiproject

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp


actual fun getHttpEngine(): HttpClientEngine = OkHttp.create()