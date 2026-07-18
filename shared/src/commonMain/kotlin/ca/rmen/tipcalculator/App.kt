package ca.rmen.tipcalculator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.rmen.tipcalculator.domain.ServiceLevel
import ca.rmen.tipcalculator.domain.TipCalculations
import ca.rmen.tipcalculator.ui.AppTheme
import ca.rmen.tipcalculator.ui.TipFormState
import ca.rmen.tipcalculator.ui.TipScreen
import ca.rmen.tipcalculator.ui.previewViewModelFactory

@Composable
fun App(
    viewModelFactory: ViewModelProvider.Factory = previewViewModelFactory,
) {
    val viewModel: TipCalculatorViewModel = viewModel(factory = viewModelFactory)
    var tipFormState by rememberSaveable(stateSaver = TipFormState.Saver) {
        mutableStateOf(
            TipFormState(
                amountWithTax = "0",
                taxAmount = "0",
                serviceLevel = ServiceLevel.GOOD,
                numberCustomer = "2",
            )
        )
    }

    AppTheme {
        val tipReportContent: List<String> by viewModel.tipReportContent.collectAsState()
        val tipCalculations: TipCalculations? by viewModel.tipCalculations.collectAsState()
        TipScreen(
            tipFormState = tipFormState,
            tipReportContent = tipReportContent,
            tipCalculations = tipCalculations,
            onStateChange = { newState ->
                tipFormState = newState
            },
            onClickCalculate = {
                tipFormState.toTipInputOrNull()?.let {
                    viewModel.calculateTip(it)
                }
            },
            onClickPrintReceipt = {
                tipFormState.toTipInputOrNull()?.let {
                    viewModel.printReceipt(it)
                }
            }
        )
    }
}

