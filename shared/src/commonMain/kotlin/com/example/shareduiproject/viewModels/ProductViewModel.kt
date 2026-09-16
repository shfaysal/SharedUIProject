package com.example.shareduiproject.viewModels

import com.example.shareduiproject.models.Product
import com.example.shareduiproject.repositories.ProductRepository
import com.example.shareduiproject.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ProductUiState {
    data object Idle : ProductUiState
    data object Loading : ProductUiState
    data class Success(val products: List<Product>) : ProductUiState
    data class Error(val message: String) : ProductUiState
}


class ProductViewModel(
    private val repository: ProductRepository,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
) {
    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Idle)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    init {
        loadProducts()
    }

    fun loadProducts(limit: Int = 20, offset: Int = 0) {
        fetchJob?.cancel()
        fetchJob = coroutineScope.launch {
            repository.getProducts().collect { result ->
                _uiState.value = when (result) {
                    is Result.Loading -> ProductUiState.Loading
                    is Result.Success -> ProductUiState.Success(result.data)
                    is Result.Error -> ProductUiState.Error(result.message)
                }
            }
        }
    }

    fun clear() {
        coroutineScope.cancel()
    }
}