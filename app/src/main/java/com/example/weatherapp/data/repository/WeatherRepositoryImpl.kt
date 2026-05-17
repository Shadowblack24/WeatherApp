package com.example.weatherapp.data.repository

import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.model.WeatherInfo
import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {

    override suspend fun getWeather(): Result<WeatherInfo> {
        return try {
            val response = weatherApi.getCurrentWeather(
                latitude = 56.0184,
                longitude = 92.8672
            )

            Result.success(
                WeatherInfo(
                    cityName = "Красноярск",
                    temperature = response.current.temperature,
                    humidity = response.current.humidity,
                    windSpeed = response.current.windSpeed
                )
            )
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}