package com.example.weathernow.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathernow.domain.data.models.Weather
import com.example.weathernow.domain.data.repository.WeatherNowRepositoryImplementation
import com.example.weathernow.domain.utils.Resource
import kotlinx.coroutines.launch

class WeatherNowViewModel(private val weatherNowRepositoryImplementation: WeatherNowRepositoryImplementation) : ViewModel() {

    private val _locationWeather = MutableLiveData<Resource<Weather>>()
    val locationWeather : LiveData<Resource<Weather>>
        get() = _locationWeather

    fun observeLocationWeather(lat: Double, lng: Double) {
        viewModelScope.launch {
            _locationWeather.run {
                postValue(Resource.loading())
                try {
                    val locationWeatherData = weatherNowRepositoryImplementation.fetchWeather()
                    postValue(Resource.success(locationWeatherData))
                } catch (e: Exception) {
                    postValue(Resource.error("Loading Weather Error!"))
                }
            }
        }
    }

}