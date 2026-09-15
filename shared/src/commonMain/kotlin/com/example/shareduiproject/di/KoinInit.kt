package com.example.shareduiproject.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}){
    startKoin {
        appDeclaration()
        modules(
            sharedAppModule,
            sharedModule,
            platformModule
        )
    }
}

//fun initKoinIos() = initKoin {}