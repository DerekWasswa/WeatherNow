package com.example.weathernow.domain.data.repository

import com.example.weathernow.domain.data.api.ApiService
import com.example.weathernow.domain.data.models.Weather

class WeatherNowRepositoryImplementation(var apiService: ApiService): WeatherNowRepository {

    override suspend fun fetchWeather(options: Map<String, String>): Weather = apiService.getWeather(options)

}