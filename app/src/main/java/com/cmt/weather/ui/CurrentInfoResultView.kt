package com.cmt.weather.ui

data class CurrentInfoResultView (
    val cityName: String,
    val cityId: String,
    val date: String,
    val maxTemp: Float,
    val minTemp: Float,
    val windPressure: Int,
    val humidity: Int,
)