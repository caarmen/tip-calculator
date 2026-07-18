package ca.rmen.tipcalculator.domain.reporter

import org.koin.core.annotation.Factory
import platform.Foundation.NSFileManager
import platform.Foundation.temporaryDirectory

@Factory
class IosReportPathProvider : ReportPathProvider {
    override fun reportPath(filename: String): String {
        val tempDir = NSFileManager.defaultManager.temporaryDirectory
        val fileUrl = tempDir.URLByAppendingPathComponent(filename)
        return fileUrl!!.path!!
    }
}