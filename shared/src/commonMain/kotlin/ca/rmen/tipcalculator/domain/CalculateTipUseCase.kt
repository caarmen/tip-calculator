package ca.rmen.tipcalculator.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CalculateTipUseCase(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(tipInput: TipInput): TipCalculations =
        withContext(dispatcher) {
            calculateTip(tipInput)
        }
}

expect fun calculateTip(
    tipInput: TipInput,
): TipCalculations