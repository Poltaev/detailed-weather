package com.example.weatheromparison.ui.data

import android.telecom.Call

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenWeatherMapApi {
    @GET("/data/2.5/forecast")
    suspend fun getOpenWeather(
        @Query("lon") lon: Double,
        @Query("lat") lat: Double,
        @Query("lang") lang: String,
        @Query("appid") appid: String,
        @Query("units") units: String
    ) : FiveDayWeatherDto

}
