package ca.rmen.tipcalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ca.rmen.tipcalculator.domain.CalculateTipUseCase
import ca.rmen.tipcalculator.domain.ReportPathProvider
import ca.rmen.tipcalculator.domain.ServiceLevel
import ca.rmen.tipcalculator.domain.TipInput
import ca.rmen.tipcalculator.ui.TipForm

@Composable
fun App(
    viewModelFactory: ViewModelProvider.Factory = previewViewModelFactory,
) {
    val viewModel: TipCalculatorViewModel = viewModel(factory = viewModelFactory)
    var amountWithTax by remember { mutableStateOf("") }
    var taxAmount by remember { mutableStateOf("") }
    var serviceLevel by remember { mutableStateOf(ServiceLevel.GOOD) }
    var numberCustomer by remember { mutableStateOf("") }

    MaterialTheme {
        val tipReportContent by viewModel.tipReportContent.collectAsState()
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TipForm(
                amountWithTax = amountWithTax,
                onAmountWithTaxChange = { amountWithTax = it },
                taxAmount = taxAmount,
                onTaxAmountChange = { taxAmount = it },
                serviceLevel = serviceLevel,
                onServiceLevelChange = { serviceLevel = it },
                numberCustomer = numberCustomer,
                onNumberCustomerChange = { numberCustomer = it },
                onCalculateClick = {
                    viewModel.calculateTip(
                        TipInput(
                            amountWithTax = amountWithTax.toDouble(),
                            taxAmount = taxAmount.toDouble(),
                            serviceLevel = serviceLevel,
                            numberCustomer = numberCustomer.toInt(),
                        )
                    )
                },
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(tipReportContent)
            }
        }
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
