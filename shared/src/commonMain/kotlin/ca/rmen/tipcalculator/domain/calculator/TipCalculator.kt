package ca.rmen.tipcalculator.domain.calculator

import ca.rmen.tipcalculator.domain.model.TipCalculations
import ca.rmen.tipcalculator.domain.model.TipInput

interface TipCalculator {
    fun calculateTip(tipInput: TipInput): TipCalculations
}