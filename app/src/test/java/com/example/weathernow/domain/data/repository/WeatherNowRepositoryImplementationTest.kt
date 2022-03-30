package com.example.weathernow.domain.data.repository

import com.example.weathernow.domain.data.api.ApiService
import com.example.weathernow.domain.data.models.City
import com.example.weathernow.domain.data.models.Coord
import com.example.weathernow.domain.data.models.Weather

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class WeatherNowRepositoryImplementationTest {
    private val apiService = Mockito.mock(ApiService::class.java)
    private var repositoryImplementation : WeatherNowRepositoryImplementation? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        repositoryImplementation = apiService?.let { WeatherNowRepositoryImplementation(it) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testsFetchLocationWeatherData() = runBlockingTest {
        Mockito.`when`(apiService?.getWeather(mapOf()))
            .thenReturn(dummyWeatherData()) //given

        val result: Weather = repositoryImplementation!!.fetchWeather(mapOf()) //when
        assert(result == dummyWeatherData())
    }

    @Test
    fun testsSettingRepositoryApiService() {
        repositoryImplementation?.apiService = apiService
        assert(repositoryImplementation?.apiService is ApiService)
    }

    private fun dummyWeatherData() : Weather {
        val city = City(id = 10L, name = "Kampala", coord = Coord(lat = 0.1, lon = 0.2), country = "UG", population = 1L, timezone = 1000L, sunrise = 1L, sunset = 1L)
        return Weather(cod = "", message = 0L, list = listOf(), city = city, cnt = 10L)
    }
}