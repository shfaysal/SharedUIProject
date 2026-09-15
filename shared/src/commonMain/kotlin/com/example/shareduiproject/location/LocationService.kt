package com.example.shareduiproject.location

import com.example.shareduiproject.models.LocationCoordinates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

expect class LocationService {
    val isTracking: StateFlow<Boolean>

    suspend fun getCurrentLocation(): LocationCoordinates?

    fun observeLocationUpdates(intervalMillis: Long): Flow<LocationCoordinates>

    fun startBackgroundTracking()

    fun stopBackgroundTracking()
}