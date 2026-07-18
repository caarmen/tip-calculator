package ca.rmen.tipcalculator.domain.reporter

import ca.rmen.tipcalculator.domain.model.TipCalculations
import ca.rmen.tipcalculator.domain.model.TipInput

interface TipReporter {
    fun generateTipReport(
        tipInput: TipInput,
        reportPath: String,
        tipCalculations: TipCalculations,
    )
}