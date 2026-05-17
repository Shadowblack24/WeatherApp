package com.example.weatherapp.domain.usecase

import com.example.weatherapp.data.local.FavoriteCityEntity
import com.example.weatherapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class DeleteFavoriteCityUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {

    suspend operator fun invoke(city: FavoriteCityEntity) {

        repository.deleteCity(city)
    }
}