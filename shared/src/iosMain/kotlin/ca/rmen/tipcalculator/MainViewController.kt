package ca.rmen.tipcalculator

import androidx.compose.ui.window.ComposeUIViewController
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ca.rmen.tipcalculator.domain.calculator.CalculateTipUseCase
import ca.rmen.tipcalculator.domain.calculator.IosTipCalculator
import ca.rmen.tipcalculator.domain.reporter.IosReportPathProvider
import ca.rmen.tipcalculator.domain.reporter.IosTipReporter
import ca.rmen.tipcalculator.domain.reporter.PrintReceiptUseCase
import ca.rmen.tipcalculator.viewmodel.TipCalculatorViewModel

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