package com.cmt.weather.ui.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CityListRep (
    @SerializedName("cities")
    @Expose
    val cityList : List<CitiesParser>
)
