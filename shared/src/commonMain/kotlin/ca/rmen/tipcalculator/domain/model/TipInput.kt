package ca.rmen.tipcalculator.domain.model

data class TipInput(
    val amountWithTax: Double,
    val taxAmount: Double,
    val serviceLevel: ServiceLevel,
    val numberCustomer: Int,
)
