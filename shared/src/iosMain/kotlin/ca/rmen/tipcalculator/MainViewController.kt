package ca.rmen.tipcalculator

import androidx.compose.ui.window.ComposeUIViewController
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ca.rmen.tipcalculator.domain.calculator.CalculateTipUseCase
import ca.rmen.tipcalculator.domain.IosReportPathProvider
import ca.rmen.tipcalculator.domain.IosTipCalculator
import ca.rmen.tipcalculator.domain.IosTipReporter
import ca.rmen.tipcalculator.domain.reporter.PrintReceiptUseCase

fun MainViewController() = ComposeUIViewController {
    App(
        viewModelFactory = viewModelFactory {
            initializer {
                TipCalculatorViewModel(
                    calculateUseCase = CalculateTipUseCase(IosTipCalculator()),
                    printUseCase = PrintReceiptUseCase(
                        reportPathProvider = IosReportPathProvider(),
                        tipReporter = IosTipReporter(),
                    )
                )
            }
        }
    )
}