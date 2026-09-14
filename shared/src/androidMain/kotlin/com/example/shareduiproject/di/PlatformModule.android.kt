package com.example.shareduiproject.di

import com.example.shareduiproject.location.LocationService
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single { LocationService(androidContext()) }
}