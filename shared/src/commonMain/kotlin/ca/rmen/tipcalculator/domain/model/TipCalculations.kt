package ca.rmen.tipcalculator.domain.model

data class TipCalculations(
    val totalWithTip: Double,
    val totalTip: Double,
    val tipPerPerson: Double,
    val pretaxAmount: Double,
    val tipPercentage: Double,
)
