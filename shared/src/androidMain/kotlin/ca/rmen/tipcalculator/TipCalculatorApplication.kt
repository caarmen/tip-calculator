package ca.rmen.tipcalculator

import android.app.Application
import ca.rmen.tipcalculator.di.initKoin
import org.koin.android.ext.koin.androidContext

class TipCalculatorApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@TipCalculatorApplication)
        }
    }
}