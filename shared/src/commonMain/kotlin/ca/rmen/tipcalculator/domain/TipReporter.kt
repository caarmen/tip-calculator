package ca.rmen.tipcalculator.domain

interface TipReporter {
    fun generateTipReport(
        tipInput: TipInput,
        reportPath: String,
        tipCalculations: TipCalculations,
    )
}