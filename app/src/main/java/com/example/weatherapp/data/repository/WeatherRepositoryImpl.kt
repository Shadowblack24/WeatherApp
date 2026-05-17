package com.example.weatherapp.data.repository

import com.example.weatherapp.data.remote.GeocodingApi
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.model.WeatherInfo
import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val geocodingApi: GeocodingApi
) : WeatherRepository {

    override suspend fun getWeather(cityName: String): Result<WeatherInfo> {
        return try {
            val cityResponse = geocodingApi.searchCity(cityName)

            val city = cityResponse.results?.firstOrNull()
                ?: return Result.failure(Exception("Город не найден"))

            val weatherResponse = weatherApi.getCurrentWeather(
                latitude = city.latitude,
                longitude = city.longitude
            )

            Result.success(
                WeatherInfo(
                    cityName = city.name,
                    temperature = weatherResponse.current.temperature,
                    humidity = weatherResponse.current.humidity,
                    windSpeed = weatherResponse.current.windSpeed
                )
            )
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}