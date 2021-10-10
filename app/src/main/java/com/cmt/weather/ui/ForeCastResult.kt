package com.cmt.weather.ui

data class ForeCastResult(
    val success: List<ForeCastResultView>? = null,
    val error: String? = null
)
