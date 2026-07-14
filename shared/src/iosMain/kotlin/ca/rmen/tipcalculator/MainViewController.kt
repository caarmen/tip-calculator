package ca.rmen.tipcalculator

import androidx.compose.ui.window.ComposeUIViewController
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ca.rmen.tipcalculator.domain.CalculateTipUseCase
import ca.rmen.tipcalculator.domain.IosReportPathProvider

fun MainViewController() = ComposeUIViewController {
    App(
        viewModelFactory = viewModelFactory {
            initializer {
                TipCalculatorViewModel(
                    useCase = CalculateTipUseCase(
                        IosReportPathProvider()
                    )
                )
            }
        }
    )
}