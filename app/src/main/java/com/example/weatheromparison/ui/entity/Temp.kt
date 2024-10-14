package com.example.weatheromparison.ui.entity

data class Temp(
    val dt: String,
    val main: DayTemperature,
    val weather: List<WeatherConditions>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val dt_txt: String
)
