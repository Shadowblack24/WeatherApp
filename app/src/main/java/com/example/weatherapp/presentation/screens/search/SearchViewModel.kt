package com.example.weatherapp.presentation.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.model.WeatherInfo
import com.example.weatherapp.domain.usecase.AddFavoriteCityUseCase
import com.example.weatherapp.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val cityQuery: String = "",
    val isLoading: Boolean = false,
    val weatherInfo: WeatherInfo? = null,
    val errorMessage: String? = null,
    val message: String? = null
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val addFavoriteCityUseCase: AddFavoriteCityUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(SearchUiState())
    val uiState: LiveData<SearchUiState> = _uiState

    fun onCityQueryChange(value: String) {
        _uiState.value = _uiState.value?.copy(
            cityQuery = value
        )
    }

    fun searchWeather() {
        val query = _uiState.value?.cityQuery.orEmpty().trim()

        if (query.isEmpty()) {
            _uiState.value = _uiState.value?.copy(
                errorMessage = "Введите название города"
            )
            return
        }

        _uiState.value = _uiState.value?.copy(
            isLoading = true,
            errorMessage = null,
            weatherInfo = null
        )

        viewModelScope.launch {
            val result = getWeatherUseCase(query)

            _uiState.value = result.fold(
                onSuccess = { weather ->
                    _uiState.value?.copy(
                        isLoading = false,
                        weatherInfo = weather,
                        errorMessage = null
                    )
                },
                onFailure = {
                    _uiState.value?.copy(
                        isLoading = false,
                        errorMessage = "Город не найден"
                    )
                }
            )
        }
    }

    fun saveCity() {
        val cityName = _uiState.value?.weatherInfo?.cityName ?: return

        viewModelScope.launch {
            val isAdded = addFavoriteCityUseCase(cityName)

            _uiState.value = _uiState.value?.copy(
                message = if (isAdded) {
                    "Город добавлен в избранное"
                } else {
                    "Этот город уже есть в избранном"
                }
            )
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value?.copy(
            message = null
        )
    }
}