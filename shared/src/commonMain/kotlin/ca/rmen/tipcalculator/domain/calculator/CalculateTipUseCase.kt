package ca.rmen.tipcalculator.domain.calculator

import ca.rmen.tipcalculator.domain.model.TipCalculations
import ca.rmen.tipcalculator.domain.model.TipInput
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
class CalculateTipUseCase(
    private val tipCalculator: TipCalculator,
    @Named("default") private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(tipInput: TipInput): TipCalculations =
        withContext(dispatcher) {
            tipCalculator.calculateTip(tipInput)
        }
}
