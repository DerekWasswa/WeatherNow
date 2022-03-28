package com.example.weathernow.domain.data.repository

import com.example.weathernow.domain.data.models.Weather

interface WeatherNowRepository {

    suspend fun fetchWeather(): Weather

}