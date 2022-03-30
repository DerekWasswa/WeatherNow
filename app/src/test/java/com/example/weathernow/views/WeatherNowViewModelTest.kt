package com.example.weathernow.views

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.weathernow.domain.data.models.Weather
import com.example.weathernow.domain.data.repository.WeatherNowRepositoryImplementation
import com.example.weathernow.domain.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import kotlin.coroutines.ContinuationInterceptor

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class WeatherNowViewModelTest {

    private lateinit var weatherNowViewModel: WeatherNowViewModel

    @Mock
    private lateinit var repositoryImplementation: WeatherNowRepositoryImplementation

    @Mock
    lateinit var weatherObserver: Observer<in Resource<Weather>>

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        weatherNowViewModel = WeatherNowViewModel(repositoryImplementation)
    }

    @ExperimentalCoroutinesApi
    @Test
    @Throws(java.lang.Exception::class)
    fun testsFetchLocationWeatherData() = runBlockingTest {
        // observe on the MutableLiveData with an observer
        weatherNowViewModel.locationWeather.observeForever(weatherObserver)
        weatherNowViewModel.observeLocationWeather(0.0, 0.0, "")

        // assertions
        Mockito.verify(weatherObserver).onChanged(Resource.loading())
        assertEquals(Resource.Status.SUCCESS, weatherNowViewModel.locationWeather.value?.status)
    }

    @ExperimentalCoroutinesApi
    @Test(expected = java.lang.Exception::class)
    fun testExceptionOnFetchLocationWeatherData() = runBlockingTest {
        Mockito.`when`(repositoryImplementation.fetchWeather(mapOf()))
            .thenThrow(Exception("Network Exception"))

        // observe on the MutableLiveData with an observer
        weatherNowViewModel.observeLocationWeather(0.0, 0.0, "")
        weatherNowViewModel.locationWeather.observeForever { weatherObserver }

        // assertions
        assertEquals(0, weatherNowViewModel.locationWeather.value?.data?.list?.size)
    }

}

@ExperimentalCoroutinesApi
class MainCoroutineRule : TestWatcher(), TestCoroutineScope by TestCoroutineScope() {

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }

}