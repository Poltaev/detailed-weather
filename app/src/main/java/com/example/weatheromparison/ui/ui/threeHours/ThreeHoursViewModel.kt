package com.example.weatheromparison.ui.ui.threeHours

import androidx.lifecycle.ViewModel
import com.example.weatheromparison.ui.domain.WeatherUseCase
import com.example.weatheromparison.ui.entity.WeatherFiveDay

class ThreeHoursViewModel : ViewModel() {
    suspend fun getThreeHoursWeather(lon: Double, lat: Double): WeatherFiveDay {
        return WeatherUseCase().getAForecastInFiveDays(lon, lat)
    }
}