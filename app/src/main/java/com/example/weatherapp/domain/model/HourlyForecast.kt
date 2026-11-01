package com.example.weatherapp.domain.model

data class HourlyForecast(
    val time: String,
    val temperature: Double,
    val weatherCode: Int,
    val precipitationProbability: Int
)