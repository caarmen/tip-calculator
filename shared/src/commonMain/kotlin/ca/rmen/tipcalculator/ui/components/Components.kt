package ca.rmen.tipcalculator.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.rmen.tipcalculator.ui.theme.AppTheme
import ca.rmen.tipcalculator.ui.theme.formBackgroundColor
import ca.rmen.tipcalculator.ui.theme.formBrightColor
import ca.rmen.tipcalculator.ui.theme.formDimColor

@Composable
fun LabeledRow(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(label.uppercase(), color = formBrightColor)
        }
        Box {
            content()
        }
        DottedDivider()
    }
}

@Composable
fun DottedDivider(modifier: Modifier = Modifier) {
    Text(
        ".".repeat(200),
        maxLines = 1,
        overflow = TextOverflow.Clip,
        color = formBrightColor,
        modifier = modifier.clearAndSetSemantics {}
    )
}

@Composable
fun LabeledTextField(
    label: String,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier,
) {
    LabeledRow(label, modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(hint) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = formBackgroundColor,
                unfocusedContainerColor = formBackgroundColor,
                disabledContainerColor = formBackgroundColor,

                focusedTextColor = formBrightColor,
                unfocusedTextColor = formBrightColor,

                cursorColor = formBrightColor,

                focusedIndicatorColor = formBrightColor,
                unfocusedIndicatorColor = formBrightColor,

                focusedLabelColor = formBrightColor,
                unfocusedLabelColor = formBrightColor,

                focusedPlaceholderColor = formDimColor,
                unfocusedPlaceholderColor = formDimColor,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview
@Composable
private fun PreviewLabeledTextField() {
    AppTheme {
        LabeledTextField(
            label = "some label",
            hint = "123.00",
            value = "",
            onValueChange = {},
            modifier = Modifier.background(formBackgroundColor)
        )
    }
}

@Composable
fun FormButton(
    label: String,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    Button(
        onClick = {
            softwareKeyboardController?.hide()
            onClick()
        },
        enabled = enabled,
        shape = RectangleShape,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = formBackgroundColor,
            contentColor = formBrightColor,
            disabledContentColor = formDimColor,
        ),
        border = BorderStroke(1.dp, formBrightColor),
        modifier = Modifier.padding(16.dp),

        ) {
        Text(label)
    }

}


@Composable
@Preview
private fun PreviewButton() {
    AppTheme {
        FormButton(
            label = "Click",
            enabled = true,
            onClick = {}
        )
    }

}