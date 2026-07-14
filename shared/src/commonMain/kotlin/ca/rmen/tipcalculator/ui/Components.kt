package ca.rmen.tipcalculator.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LabeledRow(
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
fun LabeledTextField(
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
            contentColor = formTextColor,
            disabledContentColor = formDisabledTextColor,
        ),
        border = BorderStroke(1.dp, Color(0xFF00FF66)),
        modifier = Modifier.padding(16.dp),

        ) {
        Text(label)
    }

}
