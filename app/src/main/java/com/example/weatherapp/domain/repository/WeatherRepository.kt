package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.WeatherInfo

interface WeatherRepository {

    suspend fun getWeather(cityName: String): Result<WeatherInfo>
}