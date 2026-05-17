package com.example.weatherapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_cities")
data class FavoriteCityEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val cityName: String
)