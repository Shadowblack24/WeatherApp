package com.example.weatherapp.domain.model

data class WeatherInfo(
    val cityName: String,
    val temperature: Double,
    val humidity: Int,
    val windSpeed: Double,
    val hourlyForecast: List<HourlyForecast> = emptyList()
)