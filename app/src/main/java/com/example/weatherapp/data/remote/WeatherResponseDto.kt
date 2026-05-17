package com.example.weatherapp.data.remote

import com.google.gson.annotations.SerializedName

data class WeatherResponseDto(
    @SerializedName("current")
    val current: CurrentWeatherDto
)

data class CurrentWeatherDto(
    @SerializedName("temperature_2m")
    val temperature: Double,

    @SerializedName("relative_humidity_2m")
    val humidity: Int,

    @SerializedName("wind_speed_10m")
    val windSpeed: Double
)