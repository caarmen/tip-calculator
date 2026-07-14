package ca.rmen.tipcalculator

import android.content.Context
import ca.rmen.tipcalculator.domain.AndroidReportPathProvider
import ca.rmen.tipcalculator.domain.CalculateTipUseCase
import ca.rmen.tipcalculator.domain.ReportPathProvider
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides

@DependencyGraph
interface AndroidAppGraph {
    val calculateTipUseCase: CalculateTipUseCase

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(@Provides context: Context): AndroidAppGraph
    }

    @Provides
    fun provideReportPathProvider(context: Context): ReportPathProvider = AndroidReportPathProvider(context)
}
