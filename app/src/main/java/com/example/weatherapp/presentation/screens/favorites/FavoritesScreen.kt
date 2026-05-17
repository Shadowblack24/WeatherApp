package com.example.weatherapp.presentation.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.observeAsState(
        FavoritesUiState()
    ).value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Избранные города",
            style = MaterialTheme.typography.headlineLarge
        )

        if (uiState.cities.isEmpty()) {

            Text(
                text = "Пока нет избранных городов",
                modifier = Modifier.padding(top = 24.dp)
            )
        }

        else {

            LazyColumn(
                modifier = Modifier.padding(top = 24.dp)
            ) {

                items(uiState.cities) { city ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),

                            horizontalArrangement = Arrangement.SpaceBetween,

                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = city.cityName,
                                style = MaterialTheme.typography.headlineSmall
                            )

                            Button(
                                onClick = {
                                    viewModel.deleteCity(city)
                                }
                            ) {

                                Text("Удалить")
                            }
                        }
                    }
                }
            }
        }
    }
}