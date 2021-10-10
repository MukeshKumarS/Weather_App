package com.cmt.weather.ui

data class HistoryResult(
    val success: List<HistoryResultView>? = null,
    val error: String? = null
)
