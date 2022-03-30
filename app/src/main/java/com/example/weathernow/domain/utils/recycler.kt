package com.example.weathernow.domain.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weathernow.domain.data.models.DailyWeather
import com.example.weathernow.views.WeatherAdapter

object RecyclerViewBindings {

    @JvmStatic
    @BindingAdapter("weather")
    fun setWeather(recyclerView: RecyclerView, dailyWeatherList: List<DailyWeather>?) {
        val adapter: WeatherAdapter? = recyclerView.adapter as WeatherAdapter?
        if (adapter != null && dailyWeatherList != null) {
            adapter.clearItems()
            adapter.addWeatherChanges(dailyWeatherList)
            adapter.notifyDataSetChanged()
        }
    }

}