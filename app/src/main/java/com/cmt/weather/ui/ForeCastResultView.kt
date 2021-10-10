package com.cmt.weather.ui

data class ForeCastResultView (
    val cityName: String,
    val cityId: String,
    val weather: String,
    val date: String,
    val dayTemp: Float,
    val mornTemp: Float,
    val eveTemp: Float,
    val nightTemp: Float,
    val maxTemp: Float,
    val minTemp: Float,
    val windPressure: Float,
    val humidity: Int
)