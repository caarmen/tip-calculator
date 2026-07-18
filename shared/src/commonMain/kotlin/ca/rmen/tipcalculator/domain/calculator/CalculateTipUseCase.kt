package ca.rmen.tipcalculator.domain.calculator

import ca.rmen.tipcalculator.domain.model.TipCalculations
import ca.rmen.tipcalculator.domain.model.TipInput
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CalculateTipUseCase(
    private val tipCalculator: TipCalculator,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(tipInput: TipInput): TipCalculations =
        withContext(dispatcher) {
            tipCalculator.calculateTip(tipInput)
        }
}
