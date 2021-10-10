package com.cmt.weather.ui

data class ForeCastResultView (
    val cityName: String,
    val maxTemp: Float,
    val minTemp: Float,
    val windPressure: Int,
    val Humidity: Int,
)