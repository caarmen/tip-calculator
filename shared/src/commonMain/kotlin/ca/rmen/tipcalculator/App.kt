package ca.rmen.tipcalculator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ca.rmen.gnucobol.kmp.GnuCOBOL
import ca.rmen.tipcalculator.domain.CalculateTipUseCase
import ca.rmen.tipcalculator.domain.ReportPathProvider
import ca.rmen.tipcalculator.domain.TipInput
import org.jetbrains.compose.resources.painterResource

import tipcalculator.shared.generated.resources.Res
import tipcalculator.shared.generated.resources.compose_multiplatform

@Composable
@Preview
fun App(
    reportPathProvider: ReportPathProvider = object : ReportPathProvider {
        override fun reportPath(filename: String): String {
            return "/tmp/report.txt"
        }
    }
) {
    remember {
        // This just tests all the wiring from the common compose code
        // down to COBOL.
        // TODO move this into a viewmodel/usecase...
        GnuCOBOL.initialize()
        val tipResult = CalculateTipUseCase(
            reportPathProvider,
            TipInput(
                amountWithTax = 100.0,
                taxAmount = 8.0,
                serviceLevel = 0,
                numberCustomer = 2,
            )
        ).invoke()
        println("CARM tipResult $tipResult")
    }
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}