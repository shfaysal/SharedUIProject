package com.example.shareduiproject.repositories

import com.example.shareduiproject.models.Product
import com.example.shareduiproject.service.ApiService
import com.example.shareduiproject.util.Result
import io.ktor.client.plugins.ResponseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ProductRepository {
    fun getProducts(): Flow<Result<List<Product>>>
}

class ProductRepositoryImpl(
    private val apiService: ApiService
) : ProductRepository {
    override fun getProducts(): Flow<Result<List<Product>>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getProducts()
            emit(Result.Success(response))
        } catch (e: ResponseException){
            emit(Result.Error(message = e.message ?: "", code = e.response.status.value))
        }catch (e: Exception) {
            emit(Result.Error(message = e.message ?: "An unexpected network error occurred"))
        }
    }

}