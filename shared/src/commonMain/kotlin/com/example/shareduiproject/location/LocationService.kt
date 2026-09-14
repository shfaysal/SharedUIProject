package com.example.shareduiproject.location

import com.example.shareduiproject.models.LocationCoordinates

expect class LocationService {
    suspend fun getCurrentLocation(): LocationCoordinates?
}