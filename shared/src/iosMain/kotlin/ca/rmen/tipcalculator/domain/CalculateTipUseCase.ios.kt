package ca.rmen.tipcalculator.domain

import ca.rmen.tipcalculator.TipInputRecord
import ca.rmen.tipcalculator.TipOutputRecord
import ca.rmen.tipcalculator.calculate__tip
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.alloc
import kotlinx.cinterop.ptr

actual fun calculateTip(
    tipInput: TipInput,
): TipCalculations {
    @OptIn(ExperimentalForeignApi::class)
    return memScoped {
        // --- inputs: allocate native vars and set their values ---

        val input = alloc<TipInputRecord>().apply {
            amount_with_tax = tipInput.amountWithTax
            tax_amount = tipInput.taxAmount
            service_level = tipInput.serviceLevel.value
            number_customers = tipInput.numberCustomer
        }

        // --- outputs: allocate native vars to receive results ---
        val output = alloc<TipOutputRecord>()

        calculate__tip(
            in_tip_input = input.ptr,
            out_tip_output = output.ptr,
        )
        TipCalculations(
            totalWithTip = output.total_with_tip,
            totalTip = output.total_tip,
            tipPerPerson = output.tip_per_customer,
            pretaxAmount = output.pretax_amount,
            tipPercentage = output.tip_percentage,
        )
    }
}
