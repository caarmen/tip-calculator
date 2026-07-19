package ca.rmen.tipcalculator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import ca.rmen.tipcalculator.domain.model.ServiceLevel
import ca.rmen.tipcalculator.ui.TipFormState
import ca.rmen.tipcalculator.ui.TipResultState
import ca.rmen.tipcalculator.ui.TipScreen
import ca.rmen.tipcalculator.ui.theme.AppTheme
import ca.rmen.tipcalculator.viewmodel.TipCalculatorViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val viewModel: TipCalculatorViewModel = koinViewModel()
    var tipFormState by rememberSaveable(stateSaver = TipFormState.Saver) {
        mutableStateOf(
            TipFormState(
                amountWithTax = "",
                taxAmount = "",
                serviceLevel = ServiceLevel.GOOD,
                numberCustomer = "",
            )
        )
    }

    AppTheme {
        val tipReportContent: List<String> by viewModel.tipReportContent.collectAsState()
        val tipResultState: TipResultState? by viewModel.tipResultState.collectAsState(null)
        TipScreen(
            tipFormState = tipFormState,
            tipReportContent = tipReportContent,
            tipResultState = tipResultState,
            onStateChange = { newState ->
                tipFormState = newState
                viewModel.resetCalculations()
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

