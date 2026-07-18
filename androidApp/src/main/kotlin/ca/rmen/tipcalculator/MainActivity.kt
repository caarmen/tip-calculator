package ca.rmen.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ca.rmen.tipcalculator.domain.calculator.AndroidTipCalculator
import ca.rmen.tipcalculator.domain.calculator.CalculateTipUseCase
import ca.rmen.tipcalculator.domain.reporter.AndroidReportPathProvider
import ca.rmen.tipcalculator.domain.reporter.AndroidTipReporter
import ca.rmen.tipcalculator.domain.reporter.PrintReceiptUseCase
import ca.rmen.tipcalculator.viewmodel.TipCalculatorViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        System.loadLibrary("tipcalculator")
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(
                viewModelFactory = viewModelFactory {
                    initializer {
                        TipCalculatorViewModel(
                            calculateUseCase = CalculateTipUseCase(AndroidTipCalculator()),
                            printUseCase = PrintReceiptUseCase(
                                reportPathProvider = AndroidReportPathProvider(this@MainActivity),
                                tipReporter = AndroidTipReporter(),
                            )
                        )
                    }
                }
            )
        }
    }
}
