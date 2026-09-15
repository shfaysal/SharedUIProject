package com.example.shareduiproject.di

import com.example.shareduiproject.createHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

val sharedAppModule: Module = module {

    single { createHttpClient()  }
}