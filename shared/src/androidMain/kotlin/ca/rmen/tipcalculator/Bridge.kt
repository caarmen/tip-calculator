package ca.rmen.tipcalculator

external fun handleTipRequest(
    amountWithTax: Double,
    taxAmount: Double,
    serviceLevel: Int,
    numberCustomer: Int,
    reportFilePath: String,
): DoubleArray