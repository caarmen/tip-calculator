package ca.rmen.tipcalculator.domain

data class TipCalculations(
    val totalWithTip: Double,
    val totalTip: Double,
    val tipPerPerson: Double,
    val pretaxAmount: Double,
    val tipPercentage: Double,
)
