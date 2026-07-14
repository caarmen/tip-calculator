package ca.rmen.tipcalculator.domain

import ca.rmen.tipcalculator.TipInputRecord
import ca.rmen.tipcalculator.TipOutputRecord
import ca.rmen.tipcalculator.generate__tip__report
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.alloc
import kotlinx.cinterop.ptr

actual fun generateTipReport(
    tipInput: TipInput,
    reportPath: String,
    tipCalculations: TipCalculations,
){
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
        val output = alloc<TipOutputRecord>().apply {
            total_tip = tipCalculations.totalTip
            tip_per_customer = tipCalculations.tipPerPerson
        }

        generate__tip__report(
            in_tip_input = input.ptr,
            in_report_file_path = reportPath,
            out_tip_output = output.ptr,
        )
        Unit
    }
}
