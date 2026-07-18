package ca.rmen.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ca.rmen.tipcalculator.domain.AndroidReportPathProvider
import ca.rmen.tipcalculator.domain.AndroidTipCalculator
import ca.rmen.tipcalculator.domain.CalculateTipUseCase
import ca.rmen.tipcalculator.domain.PrintReceiptUseCase

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
                                AndroidReportPathProvider(this@MainActivity)
                            )
                        )
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}