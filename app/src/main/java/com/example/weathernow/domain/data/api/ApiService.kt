package com.example.weathernow.domain.data.api

import com.example.weathernow.domain.data.models.Weather
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("data/2.5/forecast")
    suspend fun getWeather(@QueryMap options: Map<String, String>): Weather

}