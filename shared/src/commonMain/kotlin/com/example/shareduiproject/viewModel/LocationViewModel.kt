package com.example.shareduiproject.viewModel

import com.example.shareduiproject.location.LocationService
import com.example.shareduiproject.models.LocationCoordinates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed interface LocationUiState {
    data object Idle : LocationUiState
    data object Loading : LocationUiState
    data class Success(val coordinates: LocationCoordinates) : LocationUiState
    data class Error(val message: String) : LocationUiState
}

class LocationViewModel(
    private val locationService: LocationService,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
) {
    private val _uiState = MutableStateFlow<LocationUiState>(LocationUiState.Idle)
    val uiState: StateFlow<LocationUiState> = _uiState.asStateFlow()

    fun fetchLocation() {
        _uiState.value = LocationUiState.Loading
        coroutineScope.launch {
            try {
                val coords = locationService.getCurrentLocation()
                if (coords != null) {
                    _uiState.value = LocationUiState.Success(coords)
                } else {
                    _uiState.value = LocationUiState.Error("Unable to retrieve coordinates. Check permissions or GPS.")
                }
            } catch (e: Exception) {
                _uiState.value = LocationUiState.Error(e.message ?: "An unexpected error occurred.")
            }
        }
    }

    // Call from iOS when the view disappears to avoid leaking coroutines
    fun onCleared() {
        coroutineScope.cancel()
    }
}