package com.example.weatheromparison.ui.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OpenWeatherMapRepository {
    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
    private val retrofit =
        Retrofit.Builder().baseUrl("https://api.openweathermap.org").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

    private val apiOpenWeatherMap: OpenWeatherMapApi =
        retrofit.create(OpenWeatherMapApi::class.java)

    suspend fun getTheWeatherForFiveDays(
        lon: Double,
        lat: Double,
    ): FiveDayWeatherDto {
        val weatherFiveDays = apiOpenWeatherMap.getOpenWeather(
            lon,
            lat,
            "ru",
            "724d1f14a95eefe158167ac641eeaa16",
            "metric"
            )
        return weatherFiveDays
    }

}