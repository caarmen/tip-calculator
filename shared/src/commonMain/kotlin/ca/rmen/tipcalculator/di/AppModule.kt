package ca.rmen.tipcalculator.di

import ca.rmen.tipcalculator.domain.CalculateTipUseCase
import ca.rmen.tipcalculator.domain.TipInput
import org.koin.core.module.Module
import org.koin.dsl.module

fun appModule(): Module = module {
    factory { (tipInput: TipInput) -> CalculateTipUseCase(get(), tipInput) }
}
