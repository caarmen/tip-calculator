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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ca.rmen.tipcalculator.domain.model.ServiceLevel
import ca.rmen.tipcalculator.ui.components.ScaleToFitWidth
import ca.rmen.tipcalculator.ui.theme.AppTheme
import ca.rmen.tipcalculator.ui.theme.ReportTheme
import ca.rmen.tipcalculator.ui.theme.formBackgroundColor

@Composable
fun TipScreen(
    tipFormState: TipFormState,
    tipResultState: TipResultState?,
    tipReportContent: List<String>,
    onStateChange: (TipFormState) -> Unit = {},
    onClickCalculate: () -> Unit = {},
    onClickPrintReceipt: () -> Unit = {},
    modifier: Modifier = Modifier.Companion,
) {
    var showReport by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    LaunchedEffect(tipResultState) {
        if (tipResultState != null) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }
    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.primaryContainer)
            .background(formBackgroundColor)
            .safeContentPadding().fillMaxSize().verticalScroll(scrollState),
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
        tipResultState?.let {
            TipResultUi(tipResultState = it)
        }
        if (showReport && tipReportContent.isNotEmpty()) {
            @OptIn(ExperimentalMaterial3Api::class) val sheetState =
                rememberModalBottomSheetState(skipPartiallyExpanded = false)

            @OptIn(ExperimentalMaterial3Api::class)
            ModalBottomSheet(
                onDismissRequest = { showReport = false },
                sheetState = sheetState,
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState())
                ) {
                    ScaleToFitWidth(modifier = Modifier.fillMaxWidth()) {
                        ReportTheme {
                            TipReport(tipReportContent)
                        }
                    }
                }
            }
        }
    }
}

class TipCalculationsProvider : PreviewParameterProvider<TipResultState?> {
    override val values = sequenceOf(
        null,
        TipResultState(
            totalTip = "50.0",
            tipPerPerson = "25.0",
        )
    )
}

@Composable
@Preview
private fun PreviewTipScreen(
    @PreviewParameter(TipCalculationsProvider::class) tipResultState: TipResultState?
) {
    AppTheme {
        TipScreen(
            tipFormState = TipFormState(
                amountWithTax = "",
                taxAmount = "",
                serviceLevel = ServiceLevel.GOOD,
                numberCustomer = "2",
            ),
            tipResultState = tipResultState,
            tipReportContent = listOf(),
        )
    }
}
