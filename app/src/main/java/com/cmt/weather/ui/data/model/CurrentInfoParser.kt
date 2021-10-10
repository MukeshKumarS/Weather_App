package com.cmt.weather.ui.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrentInfoParser(
    @SerializedName("name")
    @Expose
    var cityName: String,
    @SerializedName("base")
    @Expose
    var base: String,
    @SerializedName("visibility")
    @Expose
    var visibility: Int,
    @SerializedName("id")
    @Expose
    var cityId: Long,
    @SerializedName("timezone")
    @Expose
    var cityTimeZone: Int,
    @SerializedName("sys")
    @Expose
    var city: SysObject,
    @SerializedName("coord")
    @Expose
    var coordinates: CoordinateObject,
    @SerializedName("wind")
    @Expose
    var wind: WindObject,
    @SerializedName("rain")
    @Expose
    var rain: RainObject,
    @SerializedName("clouds")
    @Expose
    var clouds: CloudsObject,
    @SerializedName("main")
    @Expose
    var main: MainObject,
    @SerializedName("dt")
    @Expose
    var dt: Long,
    @SerializedName("weather")
    @Expose
    var weatherData: List<CityWeather>,
) {
    data class SysObject (
        @SerializedName("type")
        @Expose
        var type: Int,
        @SerializedName("id")
        @Expose
        var id: Int,
        @SerializedName("country")
        @Expose
        var country: String,
        @SerializedName("sunrise")
        @Expose
        var sunrise: Long,
        @SerializedName("sunset")
        @Expose
        var sunset: Long
    )

    data class CityWeather (
        @SerializedName("dt")
        @Expose
        var dt: Int,
        @SerializedName("main")
        @Expose
        var main: String,
        @SerializedName("description")
        @Expose
        var description: String,
        @SerializedName("icon")
        @Expose
        var icon: String
    )

    data class CoordinateObject (
        @SerializedName("lat")
        @Expose
        var lat: Double,
        @SerializedName("lon")
        @Expose
        var lon: Double
    )

    data class WindObject (
        @SerializedName("speed")
        @Expose
        var speed: Double,
        @SerializedName("deg")
        @Expose
        var deg: Int
    )

    data class RainObject (
        @SerializedName("1h")
        @Expose
        var oneH: Float
    )

    data class CloudsObject (
        @SerializedName("all")
        @Expose
        var all: Int
    )

    data class MainObject (
        @SerializedName("temp")
        @Expose
        var temp: Float,
        @SerializedName("feels_like")
        @Expose
        var feels_like: Float,
        @SerializedName("temp_min")
        @Expose
        var temp_min: Float,
        @SerializedName("temp_max")
        @Expose
        var temp_max: Float,
        @SerializedName("pressure")
        @Expose
        var pressure: Float,
        @SerializedName("humidity")
        @Expose
        var humidity: Int,
    )
}
