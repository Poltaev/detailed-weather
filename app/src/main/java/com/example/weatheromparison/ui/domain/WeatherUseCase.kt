package com.example.weatheromparison.ui.domain

import com.example.weatheromparison.ui.data.OpenWeatherMapRepository
import com.example.weatheromparison.ui.entity.WeatherFiveDay

class WeatherUseCase {
    private val repo = OpenWeatherMapRepository()

    suspend fun getAForecastInFiveDays(lon: Double, lat: Double): WeatherFiveDay {
        return repo.getTheWeatherForFiveDays(lon, lat)
    }
}