package com.example.weathernow.domain.data.api

import com.example.weathernow.domain.data.models.Weather
import retrofit2.http.GET

interface ApiService {

    @GET("/")
    suspend fun getWeather(): Weather

}