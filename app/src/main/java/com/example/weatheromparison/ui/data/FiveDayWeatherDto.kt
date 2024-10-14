package com.example.weatheromparison.ui.data


import com.example.weatheromparison.ui.entity.City
import com.example.weatheromparison.ui.entity.Temp
import com.example.weatheromparison.ui.entity.WeatherFiveDay

class FiveDayWeatherDto (
    override val list: List<Temp>,
    override val city: City
) : WeatherFiveDay