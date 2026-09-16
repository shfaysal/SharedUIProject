package com.example.shareduiproject.di

import com.example.shareduiproject.createHttpClient
import com.example.shareduiproject.repositories.ProductRepository
import com.example.shareduiproject.repositories.ProductRepositoryImpl
import com.example.shareduiproject.service.ApiService
import com.example.shareduiproject.viewModels.ProductViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val sharedAppModule: Module = module {
    single { createHttpClient()  }
    single { ApiService(get()) }
    single <ProductRepository>{ ProductRepositoryImpl (get()) }
    factory { ProductViewModel(get()) }
}