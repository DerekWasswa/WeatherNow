package com.example.weathernow.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathernow.domain.data.models.Weather
import com.example.weathernow.domain.data.repository.WeatherNowRepositoryImplementation
import com.example.weathernow.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherNowViewModel(
    private val weatherNowRepositoryImplementation: WeatherNowRepositoryImplementation
    ) : ViewModel() {

    private val _locationWeather = MutableLiveData<Resource<Weather>>()
    val locationWeather : LiveData<Resource<Weather>>
        get() = _locationWeather

    fun observeLocationWeather(lat: Double, lng: Double, appId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _locationWeather.run {
                postValue(Resource.loading())

                val options: MutableMap<String, String> = mutableMapOf()
                options["lat"] = lat.toString()
                options["lon"] = lng.toString()
                options["appid"] = appId

                try {
                    val locationWeatherData = weatherNowRepositoryImplementation.fetchWeather(options)
                    postValue(Resource.success(locationWeatherData))
                } catch (e: Exception) {
                    e.printStackTrace()
                    postValue(Resource.error("Loading Weather Error!"))
                }
            }
        }
    }

}