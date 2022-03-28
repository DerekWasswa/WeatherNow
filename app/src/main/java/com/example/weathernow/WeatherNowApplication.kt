package com.example.weathernow

import android.app.Application
import com.example.weathernow.domain.di.appModules
import org.koin.android.ext.android.startKoin

class WeatherNowApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // set up dependency injection modules
        startKoin(this@WeatherNowApplication, listOf(appModules))
    }

}