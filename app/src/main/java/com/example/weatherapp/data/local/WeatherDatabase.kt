package com.example.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteCityEntity::class],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun favoriteCityDao(): FavoriteCityDao
}