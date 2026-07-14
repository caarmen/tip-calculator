package ca.rmen.tipcalculator.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.rmen.tipcalculator.domain.ServiceLevel
import org.jetbrains.compose.resources.stringResource
import tipcalculator.shared.generated.resources.Res
import tipcalculator.shared.generated.resources.button_calculate
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
    onCalculateClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val radioButtonColors = RadioButtonDefaults.colors(
        selectedColor = formTextColor
    )
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

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
        Button(
            onClick = {
                softwareKeyboardController?.hide()
                onCalculateClick()
            },
            enabled = tipFormState.isValid,
            shape = RectangleShape,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 0.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = formBackgroundColor,
                contentColor = formTextColor,
                disabledContentColor = formDisabledTextColor,
            ),
            border = BorderStroke(1.dp, Color(0xFF00FF66)),
            modifier = Modifier.padding(16.dp),

            ) {
            Text(stringResource(Res.string.button_calculate))
        }
    }
}

@Composable
private fun LabeledRow(
    label: String,
    modifier: Modifier = Modifier.height(56.dp),
    content: @Composable () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(label, color = formTextColor)
        }
        Box(modifier = Modifier.weight(1f)) {
            content()
        }
    }
}

@Composable
private fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    LabeledRow(label) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = formBackgroundColor,
                unfocusedContainerColor = formBackgroundColor,
                disabledContainerColor = formBackgroundColor,

                focusedTextColor = formTextColor,
                unfocusedTextColor = formTextColor,

                cursorColor = formTextColor,

                focusedIndicatorColor = formTextColor,
                unfocusedIndicatorColor = formTextColor,

                focusedLabelColor = formTextColor,
                unfocusedLabelColor = formTextColor,
            ),
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End)
        )
    }
}

