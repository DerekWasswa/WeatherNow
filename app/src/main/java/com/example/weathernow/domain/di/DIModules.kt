package com.example.weathernow.domain.di

import com.example.weathernow.domain.data.api.BaseRetrofitModule
import com.example.weathernow.domain.data.api.RetrofitModule
import com.example.weathernow.domain.data.repository.WeatherNowRepositoryImplementation
import com.example.weathernow.viewmodels.WeatherNowViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val appModules : Module = module {
    single<BaseRetrofitModule> { RetrofitModule(androidContext()) }
    viewModel{(weatherNowRepositoryImplementation: WeatherNowRepositoryImplementation) -> WeatherNowViewModel(weatherNowRepositoryImplementation) }
}