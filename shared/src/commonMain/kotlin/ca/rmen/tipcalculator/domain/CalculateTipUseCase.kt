package ca.rmen.tipcalculator.domain

class CalculateTipUseCase(
) {
    operator fun invoke(tipInput: TipInput): TipCalculations {
        return calculateTip(tipInput)
    }
}

expect fun calculateTip(
    tipInput: TipInput,
): TipCalculations