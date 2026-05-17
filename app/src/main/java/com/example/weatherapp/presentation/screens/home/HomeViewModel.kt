package com.example.weatherapp.presentation.screens.home

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

data class HomeUiState(
    val isLoading: Boolean = false,
    val weatherInfo: WeatherInfo? = null,
    val errorMessage: String? = null,
    val message: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val addFavoriteCityUseCase: AddFavoriteCityUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(HomeUiState())
    val uiState: LiveData<HomeUiState> = _uiState

    fun loadWeather(cityName: String) {
        _uiState.value = HomeUiState(isLoading = true)

        viewModelScope.launch {
            val result = getWeatherUseCase(cityName)

            _uiState.value = result.fold(
                onSuccess = { weather ->
                    HomeUiState(
                        isLoading = false,
                        weatherInfo = weather
                    )
                },
                onFailure = {
                    HomeUiState(
                        isLoading = false,
                        errorMessage = "Не удалось загрузить погоду"
                    )
                }
            )
        }
    }

    fun saveCity() {
        val cityName = uiState.value?.weatherInfo?.cityName ?: return

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