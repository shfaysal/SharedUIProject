package com.example.shareduiproject

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform