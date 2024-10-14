package com.example.weatheromparison.ui.ui.fiveDays

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatheromparison.ui.domain.WeatherUseCase
import com.example.weatheromparison.ui.entity.WeatherFiveDay

class FiveDaysViewModel : ViewModel() {
    suspend fun getFiveDayWeather(lon: Double, lat: Double): WeatherFiveDay {
        return WeatherUseCase().getAForecastInFiveDays(lon, lat)
    }
}