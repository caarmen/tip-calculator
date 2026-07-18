package ca.rmen.tipcalculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ca.rmen.tipcalculator.TipCalculatorViewModel
import ca.rmen.tipcalculator.domain.CalculateTipUseCase
import ca.rmen.tipcalculator.domain.PrintReceiptUseCase
import ca.rmen.tipcalculator.domain.ReportPathProvider
import ca.rmen.tipcalculator.domain.ServiceLevel
import ca.rmen.tipcalculator.domain.TipCalculations

@Composable
fun TipScreen(
    tipFormState: TipFormState,
    tipCalculations: TipCalculations?,
    tipReportContent: List<String>,
    onStateChange: (TipFormState) -> Unit = {},
    onClickCalculate: () -> Unit = {},
    onClickPrintReceipt: () -> Unit = {},
    modifier: Modifier = Modifier.Companion,
) {
    var showReport by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.primaryContainer)
            .background(formBackgroundColor)
            .safeContentPadding().fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TipForm(
            tipFormState = tipFormState,
            onStateChange = onStateChange,
            onClickCalculate = onClickCalculate,
            onClickPrintReceipt = {
                onClickPrintReceipt()
                showReport = true
            },
        )
        tipCalculations?.let {
            TipResultUi(tipCalculations = it)
        }
        if (showReport && tipReportContent.isNotEmpty()) {
            @OptIn(ExperimentalMaterial3Api::class) val sheetState =
                rememberModalBottomSheetState(skipPartiallyExpanded = true)

            @OptIn(ExperimentalMaterial3Api::class)
            (ModalBottomSheet(
                onDismissRequest = { showReport = false },
                sheetState = sheetState,
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState())
                ) {
                    ScaleToFitWidth(modifier = Modifier.fillMaxWidth()) {
                        TipReport(tipReportContent)
                    }
                }
            })
        }
    }
}

class TipCalculationsProvider : PreviewParameterProvider<TipCalculations?> {
    override val values = sequenceOf(
        null,
        TipCalculations(
            totalTip = 50.0,
            tipPerPerson = 25.0,
            totalWithTip = 100.0,
            pretaxAmount = 80.0,
            tipPercentage = 15.0,
        )
    )
}

@Composable
@Preview
private fun PreviewTipScreen(
    @PreviewParameter(TipCalculationsProvider::class) tipCalculations: TipCalculations?
) {
    AppTheme {
        TipScreen(
            tipFormState = TipFormState(
                amountWithTax = "",
                taxAmount = "",
                serviceLevel = ServiceLevel.GOOD,
                numberCustomer = "2",
            ),
            tipCalculations = tipCalculations,
            tipReportContent = listOf(),
        )
    }
}

val previewViewModelFactory = viewModelFactory {
    initializer {
        TipCalculatorViewModel(
            calculateUseCase = CalculateTipUseCase(),
            printUseCase = PrintReceiptUseCase(reportPathProvider = object :
                ReportPathProvider {
                override fun reportPath(filename: String) = "/tmp/report.txt"
            }
            )
        )
    }
}