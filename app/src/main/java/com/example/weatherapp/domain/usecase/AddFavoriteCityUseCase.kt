package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class AddFavoriteCityUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {

    suspend operator fun invoke(cityName: String): Boolean {
        return repository.addCity(cityName)
    }
}