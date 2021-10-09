package com.cmt.weather.ui.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForeCastInfoParser(
    @SerializedName("city")
    @Expose
    var city: CityObject,
    @SerializedName("list")
    @Expose
    var weatherData: List<CityWeather>,
) {
    data class CityObject (
        @SerializedName("geoname_id")
        @Expose
        var geoNameId: Int,
        @SerializedName("name")
        @Expose
        var cityName: String,
        @SerializedName("lat")
        @Expose
        var cityLat: Double,
        @SerializedName("lon")
        @Expose
        var cityLon: Double
    )

    data class CityWeather (
        @SerializedName("dt")
        @Expose
        var dt: Int,
        @SerializedName("pressure")
        @Expose
        var pressure: Double,
        @SerializedName("humidity")
        @Expose
        var humidity: Int,
    )
}
