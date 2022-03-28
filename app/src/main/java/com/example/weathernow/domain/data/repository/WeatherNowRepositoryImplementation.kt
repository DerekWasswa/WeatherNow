package com.example.weathernow.domain.data.repository

import com.example.weathernow.domain.data.api.ApiService
import com.example.weathernow.domain.data.models.Weather

class WeatherNowRepositoryImplementation(private val apiService: ApiService): WeatherNowRepository {

    override suspend fun fetchWeather(): Weather = apiService.getWeather()

}