package ca.rmen.tipcalculator.domain

import ca.rmen.tipcalculator.TipInputRecord
import ca.rmen.tipcalculator.TipOutputRecord
import kotlinx.cinterop.ExperimentalForeignApi
import ca.rmen.tipcalculator.handle__tip__request
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.alloc
import kotlinx.cinterop.ptr

actual fun calculateTip(
    tipInput: TipInput,
    reportPath: String,
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

        handle__tip__request(
            in_tip_input = input.ptr,
            in_report_file_path = reportPath,
            out_tip_output = output.ptr,
        )
        TipCalculations(
            totalTip = output.total_tip,
            tipPerPerson = output.tip_per_customer,
        )
    }
}
