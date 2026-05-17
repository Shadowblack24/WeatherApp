package com.example.weatherapp.presentation.screens.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.local.FavoriteCityEntity
import com.example.weatherapp.domain.usecase.DeleteFavoriteCityUseCase
import com.example.weatherapp.domain.usecase.GetFavoriteCitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FavoritesUiState(
    val cities: List<FavoriteCityEntity> = emptyList()
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteCitiesUseCase: GetFavoriteCitiesUseCase,
    private val deleteFavoriteCityUseCase: DeleteFavoriteCityUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(FavoritesUiState())
    val uiState: LiveData<FavoritesUiState> = _uiState

    init {
        loadCities()
    }

    fun loadCities() {

        viewModelScope.launch {

            val cities = getFavoriteCitiesUseCase()

            _uiState.value = FavoritesUiState(
                cities = cities
            )
        }
    }

    fun deleteCity(city: FavoriteCityEntity) {

        viewModelScope.launch {

            deleteFavoriteCityUseCase(city)

            loadCities()
        }
    }
}