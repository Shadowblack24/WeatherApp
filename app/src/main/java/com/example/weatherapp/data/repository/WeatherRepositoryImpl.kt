package com.example.weatherapp.data.repository

import com.example.weatherapp.data.remote.GeocodingApi
import com.example.weatherapp.data.remote.HourlyWeatherDto
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.model.HourlyForecast
import com.example.weatherapp.domain.model.WeatherInfo
import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val geocodingApi: GeocodingApi
) : WeatherRepository {

    override suspend fun getWeather(
        cityName: String
    ): Result<WeatherInfo> {

        return try {

            // Поиск города и получение его координат
            val cityResponse = geocodingApi.searchCity(cityName)

            val city = cityResponse.results?.firstOrNull()
                ?: return Result.failure(
                    Exception("Город не найден")
                )

            // Получение текущей погоды и почасового прогноза
            val weatherResponse = weatherApi.getCurrentWeather(
                latitude = city.latitude,
                longitude = city.longitude
            )

            // Преобразование почасовых данных
            val hourlyForecast = mapHourlyForecast(
                weatherResponse.hourly
            )

            // Формирование итоговой модели погоды
            val weatherInfo = WeatherInfo(
                cityName = city.name,
                temperature = weatherResponse.current.temperature,
                humidity = weatherResponse.current.humidity,
                windSpeed = weatherResponse.current.windSpeed,
                hourlyForecast = hourlyForecast
            )

            Result.success(weatherInfo)

        } catch (exception: Exception) {

            Result.failure(exception)
        }
    }

    /**
     * Преобразует почасовые данные API Open-Meteo
     * в список объектов HourlyForecast.
     */
    private fun mapHourlyForecast(
        hourlyDto: HourlyWeatherDto?
    ): List<HourlyForecast> {

        if (hourlyDto == null) {
            return emptyList()
        }

        val times = hourlyDto.time ?: return emptyList()

        val temperatures =
            hourlyDto.temperature ?: return emptyList()

        val weatherCodes =
            hourlyDto.weatherCode ?: return emptyList()

        val precipitationProbabilities =
            hourlyDto.precipitationProbability ?: return emptyList()

        // Определяем количество доступных записей
        val count = minOf(
            times.size,
            temperatures.size,
            weatherCodes.size,
            precipitationProbabilities.size,
            24
        )

        // Преобразуем данные в список почасовых прогнозов
        return (0 until count).map { index ->

            HourlyForecast(
                time = times[index],
                temperature = temperatures[index],
                weatherCode = weatherCodes[index],
                precipitationProbability =
                    precipitationProbabilities[index]
            )
        }
    }
}