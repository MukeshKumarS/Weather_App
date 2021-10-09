package com.cmt.weather.ui.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CitiesParser(
    @SerializedName("cityName")
    @Expose
    var cityName: String,
    @SerializedName("lat")
    @Expose
    var cityLat: Double,
    @SerializedName("lang")
    @Expose
    var cityLang: Double,
    @SerializedName("country")
    @Expose
    var country: String
)
