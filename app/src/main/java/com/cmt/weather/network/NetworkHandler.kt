package com.cmt.weather.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkHandler {
    private var retrofit: Retrofit? = null
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}