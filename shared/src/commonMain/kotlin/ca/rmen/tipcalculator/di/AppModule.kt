package ca.rmen.tipcalculator.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Singleton

@Module
@ComponentScan(
    "ca.rmen.tipcalculator.viewmodel",
    "ca.rmen.tipcalculator.domain.calculator",
    "ca.rmen.tipcalculator.domain.reporter",
)
@Configuration
class AppModule {
    @Named("default")
    @Singleton
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Named("io")
    @Singleton
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}