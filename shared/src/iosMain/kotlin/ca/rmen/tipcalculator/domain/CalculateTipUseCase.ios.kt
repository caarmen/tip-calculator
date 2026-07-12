package ca.rmen.tipcalculator.domain
import ca.rmen.tipcalculator.TipInputRecord
import ca.rmen.tipcalculator.TipOutputRecord
import kotlinx.cinterop.ExperimentalForeignApi
import ca.rmen.tipcalculator.handle__tip__request
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.ptr

actual fun calculateTip(tipInput: TipInput): TipResult {
    @OptIn(ExperimentalForeignApi::class)
    return memScoped {
        // --- inputs: allocate native vars and set their values ---

        val input = alloc<TipInputRecord>().apply {
            amount_with_tax = tipInput.amountWithTax
            tax_amount = tipInput.taxAmount
            service_level = tipInput.serviceLevel
            number_customers = tipInput.numberCustomer
        }

        val pathBuf = allocArray<ByteVar>(100) // unused for now

        // --- outputs: allocate native vars to receive results ---
        val output = alloc<TipOutputRecord>()

        handle__tip__request(
            in_tip_input = input.ptr,
            in_report_file_path = pathBuf, // todo
            out_tip_output = output.ptr,
        )
        println("result total tip: ${output.total_tip}, tip per customer: ${output.tip_per_customer}")
        println("pretax: ${output.pretax_amount}, tip percentage ${output.tip_percentage}")
        println("total with tip ${output.total_with_tip}")

        TipResult(
            totalTip = output.total_tip,
            tipPerPerson = output.tip_per_customer,
        )
    }
}
