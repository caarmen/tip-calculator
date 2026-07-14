package ca.rmen.tipcalculator.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
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
    amountWithTaxState: TextFieldState,
    taxAmountState: TextFieldState,
    serviceLevel: ServiceLevel,
    onServiceLevelChange: (ServiceLevel) -> Unit,
    numberCustomerState: TextFieldState,
    onCalculateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            state = amountWithTaxState,
            inputTransformation = doubleTransformation,
            label = { Text(stringResource(Res.string.label_amount_with_tax)) },
        )
        TextField(
            state = taxAmountState,
            inputTransformation = doubleTransformation,
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
            state = numberCustomerState,
            inputTransformation = intTransformation,
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

private val doubleTransformation = InputTransformation {
    try {
        asCharSequence().toString().toDouble()
    } catch (_: NumberFormatException) {
        revertAllChanges()
    }
}

private val intTransformation = InputTransformation {
    try {
        asCharSequence().toString().toInt()
    } catch (_: NumberFormatException) {
        revertAllChanges()
    }
}
