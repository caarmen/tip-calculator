package ca.rmen.tipcalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ca.rmen.tipcalculator.domain.CalculateTipUseCase
import ca.rmen.tipcalculator.domain.ReportPathProvider
import ca.rmen.tipcalculator.domain.ServiceLevel
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
        val tipReportContent: List<String> by viewModel.tipReportContent.collectAsState()
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
                    tipFormState.toTipInputOrNull()?.let {
                        viewModel.calculateTip(it)
                    }
                },
            )
            Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    tipReportContent.forEachIndexed { index, line ->
                        Text(
                            line,
                            fontFamily = FontFamily.Monospace,
                            softWrap = false,
                            modifier = Modifier
                                .fillMaxWidth().background(
                                    if (index % 2 == 0) Color.Green else Color.White
                                ).padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                }
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
