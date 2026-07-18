package ca.rmen.tipcalculator.domain

import android.content.Context
import ca.rmen.tipcalculator.domain.reporter.ReportPathProvider
import java.io.File

class AndroidReportPathProvider(private val context: Context) : ReportPathProvider {
    override fun reportPath(filename: String): String {
        return File.createTempFile(
            filename,
            null,
            context.cacheDir
        ).absolutePath
    }
}