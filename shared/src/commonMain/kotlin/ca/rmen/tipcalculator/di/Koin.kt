package ca.rmen.tipcalculator.di

import org.koin.dsl.KoinAppDeclaration
import org.koin.plugin.module.dsl.startKoin

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin<AppGraph> {
    appDeclaration()
}