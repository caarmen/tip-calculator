package ca.rmen.tipcalculator.domain

class CalculateTipUseCase(private val tipInput: TipInput) {
    operator fun invoke(): TipResult {
        return calculateTip(tipInput)
    }
}

expect fun calculateTip(tipInput: TipInput) : TipResult