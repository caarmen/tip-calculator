package ca.rmen.tipcalculator.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
    val textFieldColors = TextFieldDefaults.colors(
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
    )
    val textFieldStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End)
    val radioButtonColors = RadioButtonDefaults.colors(
        selectedColor = formTextColor
    )
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier.background(formBackgroundColor).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(stringResource(Res.string.label_amount_with_tax), color = formTextColor)

            }
            TextField(
                value = tipFormState.amountWithTax,
                onValueChange = { newValue ->
                    onStateChange(tipFormState.updateAmountWithTax(newValue))
                },
                colors = textFieldColors,
                modifier = Modifier.weight(1f),
                textStyle = textFieldStyle,
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(stringResource(Res.string.label_tax), color = formTextColor)
            }
            TextField(
                value = tipFormState.taxAmount,
                onValueChange = { newValue ->
                    onStateChange(tipFormState.updateTaxAmount(newValue))
                },
                colors = textFieldColors,
                modifier = Modifier.weight(1f),
                textStyle = textFieldStyle,
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart,
            ) {
                Text(stringResource(Res.string.label_service_level), color = formTextColor)
            }
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
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(stringResource(Res.string.label_number_customers), color = formTextColor)
            }
            TextField(
                value = tipFormState.numberCustomer,
                onValueChange = { newValue ->
                    onStateChange(tipFormState.updateNumberCustomer(newValue))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                ),
                colors = textFieldColors,
                modifier = Modifier.weight(1f),
                textStyle = textFieldStyle,
            )
        }
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



