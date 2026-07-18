package ca.rmen.tipcalculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.rmen.tipcalculator.domain.TipCalculations
import ca.rmen.tipcalculator.ui.components.DottedDivider
import ca.rmen.tipcalculator.ui.theme.AppTheme
import ca.rmen.tipcalculator.ui.theme.formBackgroundColor
import ca.rmen.tipcalculator.ui.theme.formLabelTextColor
import org.jetbrains.compose.resources.stringResource
import tipcalculator.shared.generated.resources.Res
import tipcalculator.shared.generated.resources.label_result_tip_per_person
import tipcalculator.shared.generated.resources.label_result_total_tip
import tipcalculator.shared.generated.resources.label_results


@Composable
fun TipResultUi(
    tipCalculations: TipCalculations = TipCalculations(
        totalWithTip = 120.0,
        totalTip = 20.0,
        tipPerPerson = 10.0,
        pretaxAmount = 92.0,
        tipPercentage = 20.0,

        ),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp).border(width = 1.dp, color = formLabelTextColor)
            .padding(16.dp),
    ) {
        Text(
            text = stringResource(Res.string.label_results).uppercase(),
            color = formLabelTextColor
        )
        DottedDivider(modifier = Modifier.padding(bottom = 16.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(Res.string.label_result_total_tip).uppercase(),
                color = formLabelTextColor
            )
            Text(text = tipCalculations.totalTip.toString(), color = formLabelTextColor)
        }
        DottedDivider(modifier = Modifier.padding(bottom = 16.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(Res.string.label_result_tip_per_person).uppercase(),
                color = formLabelTextColor
            )
            Text(text = tipCalculations.tipPerPerson.toString(), color = formLabelTextColor)
        }
    }
}

@Composable
@Preview
private fun PreviewTipResult() {
    AppTheme { TipResultUi(modifier = Modifier.background(formBackgroundColor)) }
}