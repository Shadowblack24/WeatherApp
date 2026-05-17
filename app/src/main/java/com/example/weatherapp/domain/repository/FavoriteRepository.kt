package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.local.FavoriteCityEntity

interface FavoriteRepository {

    suspend fun addCity(cityName: String): Boolean

    suspend fun getCities(): List<FavoriteCityEntity>

    suspend fun deleteCity(city: FavoriteCityEntity)
}