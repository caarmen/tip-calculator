package ca.rmen.tipcalculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.rmen.tipcalculator.domain.ServiceLevel
import org.jetbrains.compose.resources.stringResource
import tipcalculator.shared.generated.resources.Res
import tipcalculator.shared.generated.resources.button_calculate
import tipcalculator.shared.generated.resources.button_print_receipt
import tipcalculator.shared.generated.resources.label_amount_with_tax
import tipcalculator.shared.generated.resources.label_number_customers
import tipcalculator.shared.generated.resources.label_service_level
import tipcalculator.shared.generated.resources.label_tax

@Preview
@Composable
fun TipForm(
    tipFormState: TipFormState = TipFormState(
        amountWithTax = "100.0",
        taxAmount = "8.0",
        serviceLevel = ServiceLevel.GOOD,
        numberCustomer = "2",
    ),
    onStateChange: (TipFormState) -> Unit = {},
    onClickCalculate: () -> Unit = {},
    onClickPrintReceipt: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val radioButtonColors = RadioButtonDefaults.colors(
        selectedColor = formTextColor
    )

    Column(
        modifier = modifier.background(formBackgroundColor).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LabeledTextField(
            label = stringResource(Res.string.label_amount_with_tax),
            value = tipFormState.amountWithTax,
            onValueChange = { onStateChange(tipFormState.updateAmountWithTax(it)) },
        )
        LabeledTextField(
            label = stringResource(Res.string.label_tax),
            value = tipFormState.taxAmount,
            onValueChange = { onStateChange(tipFormState.updateTaxAmount(it)) },
        )
        LabeledRow(
            label = stringResource(Res.string.label_service_level),
            modifier = Modifier.wrapContentHeight(),
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                ServiceLevel.entries.forEach { candidate ->
                    Row(
                        modifier = Modifier
                            .selectable(
                                selected = tipFormState.serviceLevel == candidate,
                                onClick = {
                                    onStateChange(tipFormState.updateServiceLevel(candidate))
                                },
                                role = Role.RadioButton,
                            ).padding(vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = tipFormState.serviceLevel == candidate,
                            onClick = null,
                            colors = radioButtonColors,
                        )
                        Text(candidate.name, color = formTextColor)
                    }
                }
            }
        }
        LabeledTextField(
            label = stringResource(Res.string.label_number_customers),
            value = tipFormState.numberCustomer,
            onValueChange = { onStateChange(tipFormState.updateNumberCustomer(it)) },
            keyboardType = KeyboardType.Number,
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FormButton(
                onClick = {
                    onClickCalculate()
                },
                enabled = tipFormState.isValid,
                label = stringResource(Res.string.button_calculate),
            )
            FormButton(
                onClick = {
                    onClickPrintReceipt()
                },
                enabled = tipFormState.isValid,
                label = stringResource(Res.string.button_print_receipt),
            )
        }
    }
}
