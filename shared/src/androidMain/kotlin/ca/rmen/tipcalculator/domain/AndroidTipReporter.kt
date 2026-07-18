package ca.rmen.tipcalculator.domain

import ca.rmen.tipcalculator.domain.model.TipCalculations
import ca.rmen.tipcalculator.domain.model.TipInput
import ca.rmen.tipcalculator.domain.reporter.TipReporter

class AndroidTipReporter : TipReporter {
    override fun generateTipReport(
        tipInput: TipInput,
        reportPath: String,
        tipCalculations: TipCalculations,
    ) {
        ca.rmen.tipcalculator.generateTipReport(
            tipInput.amountWithTax,
            tipInput.taxAmount,
            tipInput.serviceLevel.value,
            tipInput.numberCustomer,
            reportPath,
            tipCalculations.totalWithTip,
            tipCalculations.totalTip,
            tipCalculations.tipPerPerson,
            tipCalculations.pretaxAmount,
            tipCalculations.tipPercentage,
        )
    }
}
