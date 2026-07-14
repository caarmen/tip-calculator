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
import androidx.compose.runtime.saveable.rememberSaveable
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
import ca.rmen.tipcalculator.ui.TipFormState

@Composable
fun App(
    viewModelFactory: ViewModelProvider.Factory = previewViewModelFactory,
) {
    val viewModel: TipCalculatorViewModel = viewModel(factory = viewModelFactory)
    var tipFormState by rememberSaveable(stateSaver = TipFormState.Saver) {
        mutableStateOf(
            TipFormState(
                amountWithTax = "",
                taxAmount = "",
                serviceLevel = ServiceLevel.GOOD,
                numberCustomer = "2",
            )
        )
    }

    MaterialTheme {
        val tipReportContent by viewModel.tipReportContent.collectAsState()
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding().fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TipForm(
                tipFormState = tipFormState,
                onStateChange = { newState ->
                    tipFormState = newState
                },
                onCalculateClick = {
                    viewModel.calculateTip(
                        TipInput(
                            amountWithTax = tipFormState.amountWithTax.toDouble(),
                            taxAmount = tipFormState.taxAmount.toDouble(),
                            serviceLevel = tipFormState.serviceLevel,
                            numberCustomer = tipFormState.numberCustomer.toInt(),
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
