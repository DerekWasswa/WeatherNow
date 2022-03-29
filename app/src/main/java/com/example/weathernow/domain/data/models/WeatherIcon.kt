package com.example.weathernow.domain.data.models

import androidx.annotation.DrawableRes
import com.example.weathernow.R

enum class WeatherIcon(
    val dayCode: String,
    val nightCode: String,
    @DrawableRes val icon: Int
) {
    CLEAR_SKY("01d", "01n", R.drawable.ic_wi_day_sunny),

    FEW_CLOUDS("02d", "02n", R.drawable.ic_wi_day_sunny_overcast),

    SCATTERED_CLOUDS("03d", "03n", R.drawable.ic_wi_cloudy),

    BROKEN_CLOUDS("04d", "04n", R.drawable.ic_wi_cloudy),

    SHOWER_RAIN("05d", "05n", R.drawable.ic_wi_rain),

    RAIN("06d", "06n", R.drawable.ic_wi_rain),

    THUNDERSTORM("07d", "07n", R.drawable.ic_wi_thunderstorm),

    SNOW("08d", "08n", R.drawable.ic_wi_cloudy),

    MIST_DAY("09d", "09n", R.drawable.ic_wi_cloudy),
}