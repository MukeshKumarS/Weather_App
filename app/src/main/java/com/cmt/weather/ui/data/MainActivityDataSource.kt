package com.cmt.weather.ui.data

import com.cmt.weather.network.NetworkHandler
import com.cmt.weather.network.NetworkRequest
import com.cmt.weather.network.NetworkResult
import com.cmt.weather.ui.data.model.CityListRep
import com.cmt.weather.ui.data.model.CurrentInfoParser
import com.cmt.weather.ui.data.model.ForeCastInfoParser
import com.cmt.weather.ui.data.model.HistoryInfoParser
import com.cmt.weather.utils.AppConstants
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityDataSource {
    private val apiRequest: NetworkRequest =
        NetworkHandler.retrofitInstance!!.create(NetworkRequest::class.java)

    fun loadCities(res: (NetworkResult<CityListRep>) -> Unit) {
        try{
            val gson = Gson()
            val cityData = gson.fromJson(AppConstants.cities, CityListRep::class.java)
            res.invoke(NetworkResult.Success(cityData))
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun fetchCurrentInfo(location: String, res: (NetworkResult<CurrentInfoParser>) -> Unit) {
        val url = AppConstants.apiBaseUrl + "weather?q=$location&APPID=${AppConstants.appKey}"
//        val apiCall: Call<CurrentInfoParser> =
//            apiRequest.getCurrentInfo(location, AppConstants.appKey)
        val apiCall: Call<CurrentInfoParser> =
            apiRequest.getCurrentInfo(url)
        apiCall.enqueue(object : Callback<CurrentInfoParser> {
            override fun onResponse(
                call: Call<CurrentInfoParser>,
                response: Response<CurrentInfoParser>
            ) {
                try {
                    val resBody = response.body()
                    if(resBody != null) {
                        res.invoke(NetworkResult.Success(resBody))
                    } else{
                        res.invoke(NetworkResult.Error(Exception("No Data")))
                    }
                } catch (e: Exception) {
                    res.invoke(NetworkResult.Error(Exception(e)))
                }
            }
            override fun onFailure(call: Call<CurrentInfoParser>, t: Throwable) {
                res.invoke(NetworkResult.Error(Exception(t)))
            }
        })
    }

    fun fetchForeCastInfo(locationId: String, res: (NetworkResult<ForeCastInfoParser>) -> Unit) {
        val url = AppConstants.sampleBaseUrl + "forecast/daily?id=$locationId&APPID=${AppConstants.appKey}"
//        val apiCall: Call<CurrentInfoParser> =
//            apiRequest.getCurrentInfo(location, AppConstants.appKey)
        val apiCall: Call<ForeCastInfoParser> =
            apiRequest.getForecastInfo(url)
        apiCall.enqueue(object : Callback<ForeCastInfoParser> {
            override fun onResponse(
                call: Call<ForeCastInfoParser>,
                response: Response<ForeCastInfoParser>
            ) {
                try {
                    val resBody = response.body()
                    if(resBody != null) {
                        res.invoke(NetworkResult.Success(resBody))
                    } else{
                        res.invoke(NetworkResult.Error(Exception("No Data")))
                    }
                } catch (e: Exception) {
                    res.invoke(NetworkResult.Error(Exception(e)))
                }
            }
            override fun onFailure(call: Call<ForeCastInfoParser>, t: Throwable) {
                res.invoke(NetworkResult.Error(Exception(t)))
            }
        })
    }

    fun fetchHistoryInfo(locationId: String, res: (NetworkResult<HistoryInfoParser>) -> Unit) {
        val url = AppConstants.sampleBaseUrl + "forecast/daily?id=$locationId&APPID=${AppConstants.appKey}"
        val apiCall: Call<HistoryInfoParser> =
            apiRequest.getHistoryInfo(url)
        apiCall.enqueue(object : Callback<HistoryInfoParser> {
            override fun onResponse(
                call: Call<HistoryInfoParser>,
                response: Response<HistoryInfoParser>
            ) {
                try {
                    val resBody = response.body()
                    if(resBody != null) {
                        res.invoke(NetworkResult.Success(resBody))
                    } else{
                        res.invoke(NetworkResult.Error(Exception("No Data")))
                    }
                } catch (e: Exception) {
                    res.invoke(NetworkResult.Error(Exception(e)))
                }
            }
            override fun onFailure(call: Call<HistoryInfoParser>, t: Throwable) {
                res.invoke(NetworkResult.Error(Exception(t)))
            }
        })
    }
}