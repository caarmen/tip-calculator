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
import ca.rmen.tipcalculator.domain.ServiceLevel
import org.jetbrains.compose.resources.stringResource
import tipcalculator.shared.generated.resources.Res
import tipcalculator.shared.generated.resources.button_calculate
import tipcalculator.shared.generated.resources.label_amount_with_tax
import tipcalculator.shared.generated.resources.label_number_customers
import tipcalculator.shared.generated.resources.label_tax

@Composable
fun TipForm(
    amountWithTax: String,
    onAmountWithTaxChange: (String) -> Unit,
    taxAmount: String,
    onTaxAmountChange: (String) -> Unit,
    serviceLevel: ServiceLevel,
    onServiceLevelChange: (ServiceLevel) -> Unit,
    numberCustomer: String,
    onNumberCustomerChange: (String) -> Unit,
    onCalculateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            value = amountWithTax,
            onValueChange = { newValue ->
                notifier(amountWithTax, newValue, ::isMoney, onAmountWithTaxChange)
            },
            label = { Text(stringResource(Res.string.label_amount_with_tax)) },
        )
        TextField(
            value = taxAmount,
            onValueChange = { newValue ->
                notifier(taxAmount, newValue, ::isMoney, onTaxAmountChange)
            },
            label = { Text(stringResource(Res.string.label_tax)) },
        )
        ServiceLevel.entries.forEach { candidate ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = serviceLevel == candidate,
                        onClick = { onServiceLevelChange(candidate) },
                        role = Role.RadioButton,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = serviceLevel == candidate,
                    onClick = null,
                )
                Text(candidate.name)
            }
        }
        TextField(
            value = numberCustomer,
            onValueChange = { newValue ->
                notifier(taxAmount, newValue, ::isInt, onNumberCustomerChange)
            },
            label = { Text(stringResource(Res.string.label_number_customers)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
            )
        )
        Button(onClick = {
            softwareKeyboardController?.hide()
            onCalculateClick()
        }) {
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
