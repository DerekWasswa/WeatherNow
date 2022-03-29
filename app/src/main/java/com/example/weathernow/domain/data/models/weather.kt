// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json    = Json(JsonConfiguration.Stable)
// val weather = json.parse(Weather.serializer(), jsonString)

package com.example.weathernow.domain.data.models

import com.example.weathernow.domain.utils.getZonedDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.sql.Timestamp
import java.time.LocalDateTime.now
import java.time.ZoneId
import java.time.ZonedDateTime

@Serializable
data class Weather (
    val cod: String,
    val message: Long,
    val cnt: Long,
    val list: List<DailyWeather>,
    val city: City
)

@Serializable
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

@Serializable
data class Coord (
    val lat: Double,
    val lon: Double
)

@Serializable
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

    @SerialName("dt_txt")
    val dtTxt: String
)

@Serializable
data class Clouds (
    val all: Long
)

@Serializable
data class MainClass (
    val temp: Double,

    @SerialName("feels_like")
    val feelsLike: Double,

    @SerialName("temp_min")
    val tempMin: Double,

    @SerialName("temp_max")
    val tempMax: Double,

    val pressure: Long,

    @SerialName("sea_level")
    val seaLevel: Long,

    @SerialName("grnd_level")
    val grndLevel: Long,

    val humidity: Long,

    @SerialName("temp_kf")
    val tempKf: Double
)

@Serializable
data class Rain (
    @SerialName("3h")
    val the3H: Double
)

@Serializable
data class Sys (
    val pod: Pod
)

@Serializable
enum class Pod(val value: String) {
    D("d"),
    N("n");

    companion object : KSerializer<Pod> {
        override val descriptor: SerialDescriptor get() {
            return PrimitiveSerialDescriptor("com.example.weathernow.domain.data.models.Pod", PrimitiveKind.STRING)
        }
        override fun deserialize(decoder: Decoder): Pod = when (val value = decoder.decodeString()) {
            "d"  -> D
            "n"  -> N
            else -> throw IllegalArgumentException("Pod could not parse: $value")
        }
        override fun serialize(encoder: Encoder, value: Pod) {
            return encoder.encodeString(value.value)
        }
    }
}

@Serializable
data class WeatherElement (
    val id: Long,
    val main: MainEnum,
    val description: Description,
    val icon: String
)

@Serializable
enum class Description(val value: String) {
    BrokenClouds("broken clouds"),
    FewClouds("few clouds"),
    LightRain("light rain"),
    ModerateRain("moderate rain"),
    OvercastClouds("overcast clouds"),
    ScatteredClouds("scattered clouds");

    companion object : KSerializer<Description> {
        override val descriptor: SerialDescriptor get() {
            return PrimitiveSerialDescriptor("com.example.weathernow.domain.data.models.Description", PrimitiveKind.STRING)
        }
        override fun deserialize(decoder: Decoder): Description = when (val value = decoder.decodeString()) {
            "broken clouds"    -> BrokenClouds
            "few clouds"       -> FewClouds
            "light rain"       -> LightRain
            "moderate rain"    -> ModerateRain
            "overcast clouds"  -> OvercastClouds
            "scattered clouds" -> ScatteredClouds
            else               -> throw IllegalArgumentException("Description could not parse: $value")
        }
        override fun serialize(encoder: Encoder, value: Description) {
            return encoder.encodeString(value.value)
        }
    }
}

@Serializable
enum class MainEnum(val value: String) {
    Clouds("Clouds"),
    Rain("Rain");

    companion object : KSerializer<MainEnum> {
        override val descriptor: SerialDescriptor get() {
            return PrimitiveSerialDescriptor("com.example.weathernow.domain.data.models.MainEnum", PrimitiveKind.STRING)
        }
        override fun deserialize(decoder: Decoder): MainEnum = when (val value = decoder.decodeString()) {
            "Clouds" -> Clouds
            "Rain"   -> Rain
            else     -> throw IllegalArgumentException("MainEnum could not parse: $value")
        }
        override fun serialize(encoder: Encoder, value: MainEnum) {
            return encoder.encodeString(value.value)
        }
    }
}

@Serializable
data class Wind (
    val speed: Double,
    val deg: Long,
    val gust: Double
)

fun WeatherElement.getIcon(): WeatherIcon {
    return WeatherIcon.values().firstOrNull { it.dayCode == icon || it.nightCode == icon }
        ?: WeatherIcon.CLEAR_SKY
}

fun List<DailyWeather>.getWeatherToday(): DailyWeather {
    var weather: DailyWeather = last()
    val timeNow = now()
    for (i in 0 until size) {
        val weatherTime = this[i].dtTxt.getZonedDateTime().toLocalDateTime()

        if (timeNow.isAfter(weatherTime)) {
            weather = this[i]
        }
    }

    return weather
}

fun List<DailyWeather>.getWeatherTomorrow(): DailyWeather {
    var weather: DailyWeather = last()
    val timeNow = now().plusDays(1)

    for (i in 0 until size) {
        val weatherTime = this[i].dtTxt.getZonedDateTime().toLocalDateTime()

        if (timeNow.isAfter(weatherTime)) {
            weather = this[i]
        }
    }

    return weather
}
