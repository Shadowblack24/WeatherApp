package com.example.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteCityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: FavoriteCityEntity)

    @Query("SELECT * FROM favorite_cities")
    suspend fun getAllCities(): List<FavoriteCityEntity>

    @Query("SELECT COUNT(*) FROM favorite_cities WHERE cityName = :cityName")
    suspend fun getCityCount(cityName: String): Int

    @Delete
    suspend fun deleteCity(city: FavoriteCityEntity)
}