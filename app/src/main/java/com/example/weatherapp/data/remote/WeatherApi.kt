package com.example.weatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast")
    suspend fun getCurrentWeather(
        @Query("latitude")
        latitude: Double,

        @Query("longitude")
        longitude: Double,

        @Query("current")
        current: String =
            "temperature_2m,relative_humidity_2m,wind_speed_10m",

        @Query("hourly")
        hourly: String =
            "temperature_2m,weather_code,precipitation_probability",

        @Query("forecast_hours")
        forecastHours: Int = 24,

        @Query("timezone")
        timezone: String = "auto"

    ): WeatherResponseDto
}