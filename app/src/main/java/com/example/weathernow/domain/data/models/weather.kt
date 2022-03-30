package com.example.weathernow.domain.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Weather (
    @SerializedName("cod")
    @Expose
    val cod: String,
    @SerializedName("message")
    @Expose
    val message: Long,
    @SerializedName("cnt")
    @Expose
    val cnt: Long,
    @SerializedName("list")
    @Expose
    val list: List<DailyWeather>,
    @SerializedName("city")
    @Expose
    val city: City
)

data class City (
    val id: Long,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Long,
    val timezone: Long,
    val sunrise: Long,
    val sunset: Long
)

data class Coord (
    val lat: Double,
    val lon: Double
)

data class DailyWeather (
    val dt: Long,
    val main: MainClass,
    val weather: List<WeatherElement>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Long,
    val pop: Double,
    val rain: Rain? = null,
    val sys: Sys,

    @SerializedName("dt_txt")
    val dtTxt: String
)

data class Clouds (
    val all: Long
)

data class MainClass (
    val temp: Double,

    @SerializedName("feels_like")
    val feelsLike: Double,

    @SerializedName("temp_min")
    val tempMin: Double,

    @SerializedName("temp_max")
    val tempMax: Double,

    val pressure: Long,

    @SerializedName("sea_level")
    val seaLevel: Long,

    @SerializedName("grnd_level")
    val grndLevel: Long,

    val humidity: Long,

    @SerializedName("temp_kf")
    val tempKf: Double
)

data class Rain (
    @SerializedName("3h")
    val the3H: Double
)

data class Sys (
    val pod: Pod
)

enum class Pod(val value: String) {
    D("d"),
    N("n");
}

data class WeatherElement (
    val id: Long,
    val main: MainEnum,
    val description: Description,
    val icon: String
)

enum class Description(val value: String) {
    BrokenClouds("broken clouds"),
    FewClouds("few clouds"),
    LightRain("light rain"),
    ModerateRain("moderate rain"),
    OvercastClouds("overcast clouds"),
    ScatteredClouds("scattered clouds");
}

enum class MainEnum(val value: String) {
    Clouds("Clouds"),
    Rain("Rain");
}

enum class ViewType {
    NORMAL, EMPTY
}

data class Wind (
    val speed: Double,
    val deg: Long,
    val gust: Double
)

fun WeatherElement.getIcon(): WeatherIcon {
    return WeatherIcon.values().firstOrNull { it.dayCode == icon || it.nightCode == icon }
        ?: WeatherIcon.CLEAR_SKY
}

fun List<DailyWeather>.sortWeatherItems(): MutableList<DailyWeather> {
    val weatherList: MutableList<DailyWeather> = this.toMutableList()
    weatherList.sortByDescending { it.dtTxt }
    return weatherList
}
