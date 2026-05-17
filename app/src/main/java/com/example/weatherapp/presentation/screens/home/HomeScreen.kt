package com.example.weatherapp.presentation.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    selectedCityName: String?,
    isSelectedCityFavorite: Boolean,
    onOpenSearchClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.observeAsState(
        HomeUiState()
    ).value

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val configuration = LocalConfiguration.current

    val isLandscape =
        configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(selectedCityName) {
        if (!selectedCityName.isNullOrBlank()) {
            viewModel.loadWeather(selectedCityName)
        }
    }

    LaunchedEffect(uiState.message) {
        uiState.message?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearMessage()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =
            if (isLandscape) Arrangement.Top
            else Arrangement.Center
    ) {
        SnackbarHost(
            hostState = snackbarHostState
        )

        Text(
            text = "Погода",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (selectedCityName.isNullOrBlank()) {
            Text(
                text = "Город не выбран"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onOpenSearchClick
            ) {
                Text("Выбрать город")
            }

            return@Column
        }

        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }

            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.loadWeather(selectedCityName)
                    }
                ) {
                    Text("Повторить")
                }
            }

            uiState.weatherInfo != null -> {
                val weather = uiState.weatherInfo

                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = weather.cityName,
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "${weather.temperature} °C",
                            style = MaterialTheme.typography.displaySmall
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Влажность: ${weather.humidity}%"
                        )

                        Text(
                            text = "Ветер: ${weather.windSpeed} км/ч"
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        if (isSelectedCityFavorite) {
                            Text(
                                text = "Город уже в избранном",
                                color = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            Button(
                                onClick = {
                                    viewModel.saveCity()
                                }
                            ) {
                                Text("Добавить в избранное")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.loadWeather(selectedCityName)
                    }
                ) {
                    Text("Обновить")
                }
            }
        }
    }
}