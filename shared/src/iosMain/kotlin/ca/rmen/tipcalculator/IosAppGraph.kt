package ca.rmen.tipcalculator

import ca.rmen.tipcalculator.domain.CalculateTipUseCase
import ca.rmen.tipcalculator.domain.IosReportPathProvider
import ca.rmen.tipcalculator.domain.ReportPathProvider
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides

@DependencyGraph
interface IosAppGraph {
    val calculateTipUseCase: CalculateTipUseCase

    @Provides
    fun provideReportPathProvider(): ReportPathProvider = IosReportPathProvider()
}
