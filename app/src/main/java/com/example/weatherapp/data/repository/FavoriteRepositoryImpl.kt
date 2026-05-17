package com.example.weatherapp.data.repository

import com.example.weatherapp.data.local.FavoriteCityDao
import com.example.weatherapp.data.local.FavoriteCityEntity
import com.example.weatherapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteCityDao
) : FavoriteRepository {

    override suspend fun addCity(cityName: String) {

        dao.insertCity(
            FavoriteCityEntity(
                cityName = cityName
            )
        )
    }

    override suspend fun getCities(): List<FavoriteCityEntity> {

        return dao.getAllCities()
    }

    override suspend fun deleteCity(city: FavoriteCityEntity) {

        dao.deleteCity(city)
    }
}