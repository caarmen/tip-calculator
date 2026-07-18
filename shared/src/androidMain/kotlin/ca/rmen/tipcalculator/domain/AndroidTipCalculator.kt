package ca.rmen.tipcalculator.domain

class AndroidTipCalculator : TipCalculator {
    override fun calculateTip(
        tipInput: TipInput,
    ): TipCalculations {
        val result: DoubleArray = ca.rmen.tipcalculator.calculateTip(
            tipInput.amountWithTax,
            tipInput.taxAmount,
            tipInput.serviceLevel.value,
            tipInput.numberCustomer,
        )
        return TipCalculations(
            totalWithTip = result[0],
            totalTip = result[1],
            tipPerPerson = result[2],
            pretaxAmount = result[3],
            tipPercentage = result[4],
        )
    }
}
