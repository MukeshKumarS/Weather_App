package com.cmt.weather.ui.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForeCastInfoParser(
    @SerializedName("city")
    @Expose
    var city: CityObject,
    @SerializedName("cnt")
    @Expose
    var cnt: String,
    @SerializedName("list")
    @Expose
    var weatherData: List<CityWeather>,
) {
    data class CityObject(
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
        var cityLon: Double,
        @SerializedName("country")
        @Expose
        var country: String,
        @SerializedName("iso2")
        @Expose
        var iso2: String,
        @SerializedName("type")
        @Expose
        var type: String,
    )

    data class CityWeather(
        @SerializedName("dt")
        @Expose
        var dt: Int,
        @SerializedName("pressure")
        @Expose
        var pressure: Double,
        @SerializedName("speed")
        @Expose
        var speed: Float,
        @SerializedName("snow")
        @Expose
        var snow: Float,
        @SerializedName("humidity")
        @Expose
        var humidity: Int,
        @SerializedName("deg")
        @Expose
        var deg: Int,
        @SerializedName("clouds")
        @Expose
        var clouds: Int,
        @SerializedName("weather")
        @Expose
        var weatherData: List<Weather>,
        @SerializedName("temp")
        @Expose
        var temp: CityTemperatures
    ) {

        data class Weather(
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

        data class CityTemperatures(
            @SerializedName("day")
            @Expose
            var day: Float,
            @SerializedName("min")
            @Expose
            var min: Float,
            @SerializedName("max")
            @Expose
            var max: Float,
            @SerializedName("night")
            @Expose
            var night: Float,
            @SerializedName("morn")
            @Expose
            var morn: Float,
            @SerializedName("eve")
            @Expose
            var eve: Float
        )
    }

}
