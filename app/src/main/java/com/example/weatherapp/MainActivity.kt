package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.presentation.components.DrawerContent
import com.example.weatherapp.presentation.navigation.Screen
import com.example.weatherapp.presentation.screens.favorites.FavoritesScreen
import com.example.weatherapp.presentation.screens.home.HomeScreen
import com.example.weatherapp.presentation.screens.search.SearchScreen
import com.example.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeatherAppTheme {

                var selectedCityName by remember {
                    mutableStateOf<String?>(null)
                }

                var isSelectedCityFavorite by remember {
                    mutableStateOf(false)
                }

                val drawerState = rememberDrawerState(
                    initialValue = DrawerValue.Closed
                )

                val scope = rememberCoroutineScope()
                val navController = rememberNavController()

                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        DrawerContent { route ->
                            navController.navigate(route) {
                                launchSingleTop = true
                            }

                            scope.launch {
                                drawerState.close()
                            }
                        }
                    }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text("Погода+")
                                },
                                navigationIcon = {
                                    TextButton(
                                        onClick = {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }
                                    ) {
                                        Text("Меню")
                                    }
                                },
                                actions = {
                                    if (currentRoute != Screen.Home.route) {
                                        TextButton(
                                            onClick = {
                                                val isBackSuccessful =
                                                    navController.popBackStack()

                                                if (!isBackSuccessful) {
                                                    navController.navigate(Screen.Home.route) {
                                                        launchSingleTop = true
                                                    }
                                                }
                                            }
                                        ) {
                                            Text("Назад")
                                        }
                                    }
                                }
                            )
                        }
                    ) { paddingValues ->

                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            NavHost(
                                navController = navController,
                                startDestination = Screen.Home.route
                            ) {
                                composable(Screen.Home.route) {
                                    HomeScreen(
                                        selectedCityName = selectedCityName,
                                        isSelectedCityFavorite = isSelectedCityFavorite,
                                        onOpenSearchClick = {
                                            navController.navigate(Screen.Search.route)
                                        }
                                    )
                                }

                                composable(Screen.Search.route) {
                                    SearchScreen(
                                        onCitySelected = { cityName ->
                                            selectedCityName = cityName
                                            isSelectedCityFavorite = false

                                            navController.navigate(Screen.Home.route) {
                                                launchSingleTop = true
                                            }
                                        }
                                    )
                                }

                                composable(Screen.Favorites.route) {
                                    FavoritesScreen(
                                        onCitySelected = { cityName ->
                                            selectedCityName = cityName
                                            isSelectedCityFavorite = true

                                            navController.navigate(Screen.Home.route) {
                                                launchSingleTop = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}