package com.example.weatherapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.presentation.navigation.Screen

@Composable
fun DrawerContent(
    onItemClick: (String) -> Unit
) {

    ModalDrawerSheet {

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Weather App",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        HorizontalDivider()

        NavigationDrawerItem(
            label = {
                Text("Главная")
            },

            selected = false,

            onClick = {
                onItemClick(Screen.Home.route)
            }
        )

        NavigationDrawerItem(
            label = {
                Text("Поиск")
            },

            selected = false,

            onClick = {
                onItemClick(Screen.Search.route)
            }
        )

        NavigationDrawerItem(
            label = {
                Text("Избранное")
            },

            selected = false,

            onClick = {
                onItemClick(Screen.Favorites.route)
            }
        )
    }
}