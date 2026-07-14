package ca.rmen.tipcalculator.domain

import platform.Foundation.NSFileManager
import platform.Foundation.temporaryDirectory

class IosReportPathProvider : ReportPathProvider {
    override fun reportPath(filename: String): String {
        val tempDir = NSFileManager.defaultManager.temporaryDirectory
        val fileUrl = tempDir.URLByAppendingPathComponent(filename)
        return fileUrl!!.path!!
    }
}