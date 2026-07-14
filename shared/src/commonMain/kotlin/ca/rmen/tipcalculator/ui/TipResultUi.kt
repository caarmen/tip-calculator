package ca.rmen.tipcalculator.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.rmen.tipcalculator.domain.TipCalculations
import org.jetbrains.compose.resources.stringResource
import tipcalculator.shared.generated.resources.Res
import tipcalculator.shared.generated.resources.label_result_tip_per_person
import tipcalculator.shared.generated.resources.label_result_total_tip


@Preview
@Composable
fun TipResultUi(
    tipCalculations: TipCalculations = TipCalculations(
        totalTip = 20.0,
        tipPerPerson = 10.0,
    ),
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        LabeledRow(label = stringResource(Res.string.label_result_total_tip)) {
            Text(text = tipCalculations.totalTip.toString(), color = formTextColor)
        }
        LabeledRow(label = stringResource(Res.string.label_result_tip_per_person)) {
            Text(text = tipCalculations.tipPerPerson.toString(), color = formTextColor)
        }
    }
}