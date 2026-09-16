package com.example.shareduiproject.service

import com.example.shareduiproject.models.Product
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ApiService(
    private val client: HttpClient
) {

    suspend fun getProducts(
    ) : List<Product> {
        return client.get("products").body()
    }
}