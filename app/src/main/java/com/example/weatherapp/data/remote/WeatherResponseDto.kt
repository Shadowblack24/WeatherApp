package com.example.weatherapp.data.remote

import com.google.gson.annotations.SerializedName

data class WeatherResponseDto(

    @SerializedName("current")
    val current: CurrentWeatherDto,

    @SerializedName("hourly")
    val hourly: HourlyWeatherDto? = null,

    @SerializedName("timezone")
    val timezone: String? = null
)

data class CurrentWeatherDto(

    @SerializedName("temperature_2m")
    val temperature: Double,

    @SerializedName("relative_humidity_2m")
    val humidity: Int,

    @SerializedName("wind_speed_10m")
    val windSpeed: Double
)

data class HourlyWeatherDto(

    @SerializedName("time")
    val time: List<String>? = null,

    @SerializedName("temperature_2m")
    val temperature: List<Double>? = null,

    @SerializedName("weather_code")
    val weatherCode: List<Int>? = null,

    @SerializedName("precipitation_probability")
    val precipitationProbability: List<Int>? = null
)