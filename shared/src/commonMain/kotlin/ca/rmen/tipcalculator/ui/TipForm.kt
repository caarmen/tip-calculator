package ca.rmen.tipcalculator.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import ca.rmen.tipcalculator.TipCalculatorViewModel
import ca.rmen.tipcalculator.domain.ServiceLevel
import ca.rmen.tipcalculator.domain.TipInput
import org.jetbrains.compose.resources.stringResource
import tipcalculator.shared.generated.resources.Res
import tipcalculator.shared.generated.resources.button_calculate
import tipcalculator.shared.generated.resources.label_amount_with_tax
import tipcalculator.shared.generated.resources.label_number_customers
import tipcalculator.shared.generated.resources.label_tax

@Composable
fun TipForm(
    viewModel: TipCalculatorViewModel,
    modifier: Modifier = Modifier,
) {
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    val amountWithTaxState = rememberTextFieldState()
    val taxAmountState = rememberTextFieldState()
    var serviceLevelState by remember { mutableStateOf(ServiceLevel.GOOD) }
    val numberCustomerState = rememberTextFieldState()
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
        ServiceLevel.entries.forEach { serviceLevel ->
            Row(
                modifier = Modifier.fillMaxWidth()
                    .selectable(
                        selected = serviceLevelState == serviceLevel,
                        onClick = {
                            serviceLevelState = serviceLevel
                        },
                        role = Role.RadioButton
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = serviceLevelState == serviceLevel,
                    onClick = null // Row handles click
                )
                Text(serviceLevel.name)
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
            viewModel.calculateTip(
                TipInput(
                    amountWithTax = amountWithTaxState.text.toString().toDouble(),
                    taxAmount = taxAmountState.text.toString().toDouble(),
                    serviceLevel = serviceLevelState,
                    numberCustomer = numberCustomerState.text.toString().toInt(),
                )
            )
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

