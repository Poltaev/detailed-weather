package com.example.weatheromparison.ui.ui.fiveDays

import androidx.lifecycle.ViewModel
import com.example.weatheromparison.ui.domain.WeatherUseCase
import com.example.weatheromparison.ui.entity.Temp
import com.example.weatheromparison.ui.entity.WeatherFiveDay

class TodayViewModel : ViewModel() {
    suspend fun getTodaYWeather(lon: Double, lat: Double): WeatherFiveDay {
       return WeatherUseCase().getAForecastInFiveDays(lon, lat)
    }
}