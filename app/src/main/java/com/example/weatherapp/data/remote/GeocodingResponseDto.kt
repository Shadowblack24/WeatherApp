package com.example.weatherapp.data.remote

import com.google.gson.annotations.SerializedName

data class GeocodingResponseDto(
    @SerializedName("results")
    val results: List<GeoCityDto>?
)

data class GeoCityDto(
    @SerializedName("name")
    val name: String,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("country")
    val country: String?,

    @SerializedName("admin1")
    val region: String?
)