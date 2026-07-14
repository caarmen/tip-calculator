package ca.rmen.tipcalculator

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ca.rmen.tipcalculator.domain.CalculateTipUseCase
import ca.rmen.tipcalculator.domain.ReportPathProvider
import ca.rmen.tipcalculator.domain.ServiceLevel
import ca.rmen.tipcalculator.domain.TipInput
import org.jetbrains.compose.resources.painterResource
import tipcalculator.shared.generated.resources.Res
import tipcalculator.shared.generated.resources.compose_multiplatform

@Composable
fun App(
    viewModelFactory: ViewModelProvider.Factory = previewViewModelFactory,
) {
    val viewModel: TipCalculatorViewModel = viewModel(factory = viewModelFactory)
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    MaterialTheme {
        val amountWithTaxState = rememberTextFieldState()
        val taxAmountState = rememberTextFieldState()
        var serviceLevelState by remember { mutableStateOf(ServiceLevel.GOOD) }
        val numberCustomerState = rememberTextFieldState()
        val tipCalculations by viewModel.tipCalculations.collectAsState()
        val tipReportContent by viewModel.tipReportContent.collectAsState()
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding().fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                state = amountWithTaxState,
                inputTransformation = doubleTransformation,
                label = { Text("Bill amount (incl tax)") },
            )
            TextField(
                state = taxAmountState,
                inputTransformation = doubleTransformation,
                label = { Text("Tax") },
            )
            ServiceLevel.entries.forEach { serviceLevel ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .selectable(
                            selected = serviceLevelState == serviceLevel,
                            onClick = {
                                serviceLevelState = serviceLevel
                            },
                            role = Role.RadioButton
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(
                        selected = serviceLevelState == serviceLevel,
                        onClick = null // Row handles click
                    )
                    Text(serviceLevel.name)
                }

            }
            TextField(
                state = numberCustomerState,
                inputTransformation = intTransformation,
                label = { Text("Number of customers") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                )
            )

            Button(onClick = {
                softwareKeyboardController?.hide()
                viewModel.calculateTip(
                    TipInput(
                        amountWithTax = amountWithTaxState.text.toString().toDouble(),
                        taxAmount = taxAmountState.text.toString().toDouble(),
                        serviceLevel = serviceLevelState,
                        numberCustomer = numberCustomerState.text.toString().toInt(),
                    )
                )
            }) {
                Text("Click me!")
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(tipReportContent)
            }
        }
    }
}


private val doubleTransformation = InputTransformation {
    try {
        asCharSequence().toString().toDouble()
    } catch (_: NumberFormatException) {
        revertAllChanges()
    }
}

private val intTransformation = InputTransformation {
    try {
        asCharSequence().toString().toInt()
    } catch (_: NumberFormatException) {
        revertAllChanges()
    }
}


val previewViewModelFactory = viewModelFactory {
    initializer {
        TipCalculatorViewModel(useCase = CalculateTipUseCase(reportPathProvider = object :
            ReportPathProvider {
            override fun reportPath(filename: String) = "/tmp/report.txt"
        }))
    }
}