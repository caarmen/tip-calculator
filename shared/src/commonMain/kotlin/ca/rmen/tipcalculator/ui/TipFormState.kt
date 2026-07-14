package ca.rmen.tipcalculator.ui

import ca.rmen.tipcalculator.domain.ServiceLevel

data class TipFormState(
    val amountWithTax: String,
    val taxAmount: String,
    val serviceLevel: ServiceLevel,
    val numberCustomer: String,
) {
    val canSubmit: Boolean
        get() = amountWithTax.isNotBlank() && taxAmount.isNotBlank() && numberCustomer.isNotBlank()
}
