package com.cmt.weather.network

import com.cmt.weather.ui.data.model.CurrentInfoParser
import com.cmt.weather.ui.data.model.ForeCastInfoParser
import com.cmt.weather.ui.data.model.HistoryInfoParser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NetworkRequest {

//    @GET("weather")
//    fun getCurrentInfo(
//        @Query("q") place: String,
//        @Query("appid") appKey:String
//    ): Call<CurrentInfoParser>

//    @GET("forecast/daily")
//    fun getForeCast(
//        @Query("id") placeId: Int,
//        @Query("appid") appKey:String
//    ): Call<ForecastInfoParser>

    @GET
    fun getCurrentInfo(
        @Url url: String
    ): Call<CurrentInfoParser>

    @GET
    fun getForecastInfo(
        @Url url: String
    ): Call<ForeCastInfoParser>

    @GET
    fun getHistoryInfo(
        @Url url: String
    ): Call<HistoryInfoParser>

}