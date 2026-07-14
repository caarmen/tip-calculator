package ca.rmen.tipcalculator.domain

interface ReportPathProvider {
    /**
     * @return the full path to a writeable file of the given filename.
     */
    fun reportPath(filename: String): String
}