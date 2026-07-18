package ca.rmen.tipcalculator.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
            Text(label.uppercase(), color = formLabelTextColor)
        }
        Box {
            content()
        }
        Text(
            ".".repeat(200),
            maxLines = 1,
            overflow = TextOverflow.Clip,
            color = formLabelTextColor,
            modifier = Modifier.clearAndSetSemantics {}
        )
    }
}

@Composable
fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier,
) {
    LabeledRow(label, modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = formBackgroundColor,
                unfocusedContainerColor = formBackgroundColor,
                disabledContainerColor = formBackgroundColor,

                focusedTextColor = formInputTextColor,
                unfocusedTextColor = formInputTextColor,

                cursorColor = formInputTextColor,

                focusedIndicatorColor = formLabelTextColor,
                unfocusedIndicatorColor = formLabelTextColor,

                focusedLabelColor = formInputTextColor,
                unfocusedLabelColor = formInputTextColor,
            ),
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End)
        )
    }
}

@Preview
@Composable
private fun PreviewLabeledTextField() {
    AppTheme {
        LabeledTextField(
            label = "some label",
            value = "123",
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
            contentColor = formInputTextColor,
            disabledContentColor = formDisabledTextColor,
        ),
        border = BorderStroke(1.dp, formLabelTextColor),
        modifier = Modifier.padding(16.dp),

        ) {
        Text(label)
    }

}
