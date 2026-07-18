package ca.rmen.tipcalculator.domain

interface TipCalculator {
    fun calculateTip(tipInput: TipInput): TipCalculations
}