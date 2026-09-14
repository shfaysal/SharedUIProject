package com.example.shareduiproject.di

import com.example.shareduiproject.viewModel.LocationViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val sharedModule = module {

    factory { LocationViewModel(get()) }

}

expect val platformModule: Module