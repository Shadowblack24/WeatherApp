package com.example.weatherapp.presentation.navigation

sealed class Screen(val route: String) {

    data object Home : Screen("home")

    data object Search : Screen("search")

    data object Favorites : Screen("favorites")
}