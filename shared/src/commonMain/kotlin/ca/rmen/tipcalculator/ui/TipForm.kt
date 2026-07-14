package ca.rmen.tipcalculator.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import ca.rmen.tipcalculator.domain.ServiceLevel
import org.jetbrains.compose.resources.stringResource
import tipcalculator.shared.generated.resources.Res
import tipcalculator.shared.generated.resources.button_calculate
import tipcalculator.shared.generated.resources.label_amount_with_tax
import tipcalculator.shared.generated.resources.label_number_customers
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
    onAmountWithTaxChange: (String) -> Unit = {},
    onTaxAmountChange: (String) -> Unit = {},
    onServiceLevelChange: (ServiceLevel) -> Unit = {},
    onNumberCustomerChange: (String) -> Unit = {},
    onCalculateClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            value = tipFormState.amountWithTax,
            onValueChange = { newValue ->
                notifier(tipFormState.amountWithTax, newValue, ::isMoney, onAmountWithTaxChange)
            },
            label = { Text(stringResource(Res.string.label_amount_with_tax)) },
        )
        TextField(
            value = tipFormState.taxAmount,
            onValueChange = { newValue ->
                notifier(tipFormState.taxAmount, newValue, ::isMoney, onTaxAmountChange)
            },
            label = { Text(stringResource(Res.string.label_tax)) },
        )
        ServiceLevel.entries.forEach { candidate ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = tipFormState.serviceLevel == candidate,
                        onClick = { onServiceLevelChange(candidate) },
                        role = Role.RadioButton,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = tipFormState.serviceLevel == candidate,
                    onClick = null,
                )
                Text(candidate.name)
            }
        }
        TextField(
            value = tipFormState.numberCustomer,
            onValueChange = { newValue ->
                notifier(tipFormState.numberCustomer, newValue, ::isInt, onNumberCustomerChange)
            },
            label = { Text(stringResource(Res.string.label_number_customers)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
            )
        )
        Button(
            onClick = {
                softwareKeyboardController?.hide()
                onCalculateClick()
            },
            enabled = tipFormState.canSubmit,
        ) {
            Text(stringResource(Res.string.button_calculate))
        }
    }
}

private fun notifier(
    oldValue: String,
    newValue: String,
    filter: (String) -> Boolean,
    callback: (String) -> Unit
) {
    if (oldValue != newValue && filter(newValue)) {
        callback(newValue)
    }
}

private fun isMoney(value: String): Boolean {
    if (value.isBlank()) return true
    try {
        value.toDouble()
        return true
    } catch (_: NumberFormatException) {
        return false
    }
}

private fun isInt(value: String): Boolean {
    if (value.isBlank()) return true
    try {
        value.toInt()
        return true
    } catch (_: NumberFormatException) {
        return false
    }
}
