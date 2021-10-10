package com.cmt.weather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.weather.network.NetworkResult
import com.cmt.weather.ui.data.MainActivityRepository
import com.cmt.weather.ui.data.model.CitiesParser
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivityViewModel(val activityRepository: MainActivityRepository) : ViewModel() {
    private var _getCityList = MutableLiveData<List<CitiesParser>>()
    var getCityList: LiveData<List<CitiesParser>> = _getCityList
    private var _strGetCityList = MutableLiveData<List<String>>()
    var strGetCityList: LiveData<List<String>> = _strGetCityList
    private var _fetchCurrentInfo = MutableLiveData<CurrentInfoResult>()
    var fetchCurrentInfo: LiveData<CurrentInfoResult> = _fetchCurrentInfo
    private var _fetchForeCastResult = MutableLiveData<ForeCastResult>()
    var fetchForeCastResult: LiveData<ForeCastResult> = _fetchForeCastResult
    private var _isNetworkLost = MutableLiveData<Boolean>()
    var isNetworkLost: LiveData<Boolean> = _isNetworkLost

    fun loadCities() {
        activityRepository.loadCities { res ->
            if (res is NetworkResult.Success) {
                val cityList = res.data.cityList
                val strCityArr = ArrayList<String>()
                for (city in cityList) {
                    strCityArr.add(city.cityName)
                }
                _strGetCityList.value = strCityArr
                _getCityList.value = cityList
            }
        }
    }

    fun fetchCityCurrentInfo(location: String) {
        activityRepository.fetchCurrentInfo(location) { response ->
            if (response is NetworkResult.Success) {
                val currentInfo = response.data
                val weatherList = currentInfo.weatherData
                var strWeather = ""
                for(weather in weatherList){
                    strWeather += "${weather.main}, "
                }
                strWeather = strWeather.substring(0, strWeather.length - 2)
                val currentInfoResultView = CurrentInfoResultView(
                    cityName = currentInfo.cityName,
                    cityId = currentInfo.cityId.toString(),
                    weather = strWeather,
                    date = convertMillToDate(currentInfo.dt),
                    maxTemp = currentInfo.main.temp_max,
                    minTemp = currentInfo.main.temp_min,
                    windPressure = currentInfo.main.pressure,
                    humidity = currentInfo.main.humidity
                )
                _fetchCurrentInfo.value = CurrentInfoResult(success = currentInfoResultView)
            } else {
                if (response is NetworkResult.Error) {
                    val error = response.exception
                    val errorMsg = "Please try again!"
                    if(error.message?.isNotEmpty() == true)
                        _fetchCurrentInfo.value = CurrentInfoResult(error = error.message ?: "Please try again")
                    else
                        _fetchCurrentInfo.value = CurrentInfoResult(error = errorMsg)
                }
            }
        }
    }

    fun fetchForeCastInfo(locationId: String) {
        activityRepository.fetchForeCastInfo(locationId) { response ->
            if (response is NetworkResult.Success) {
                val currentInfo = response.data
                val weatherDataList = currentInfo.weatherData
                val foreCastArrayList = ArrayList<ForeCastResultView>()
                for (foreCastList in weatherDataList) {
                    val weatherList = foreCastList.weatherList
                    var strWeather = ""
                    for (weather in weatherList) {
                        strWeather += "${weather.main}, "
                    }
                    strWeather = strWeather.substring(0, strWeather.length - 2)
                    val foreCastResultView = ForeCastResultView(
                        cityName = currentInfo.city.cityName,
                        cityId = currentInfo.city.geoNameId.toString(),
                        weather = strWeather,
                        date = convertMillToDate(foreCastList.dt),
                        maxTemp = foreCastList.temp.max,
                        minTemp = foreCastList.temp.min,
                        dayTemp = foreCastList.temp.day,
                        mornTemp = foreCastList.temp.morn,
                        eveTemp = foreCastList.temp.eve,
                        nightTemp = foreCastList.temp.night,
                        windPressure = foreCastList.pressure,
                        humidity = foreCastList.humidity
                    )
                    foreCastArrayList.add(foreCastResultView)
                }
                _fetchForeCastResult.value = ForeCastResult(success = foreCastArrayList)
            } else {
                if (response is NetworkResult.Error) {
                    val error = response.exception
                    val errorMsg = "Please try again!"
                    if(error.message?.isNotEmpty() == true)
                        _fetchForeCastResult.value = ForeCastResult(error = error.message ?: "Please try again")
                    else
                        _fetchForeCastResult.value = ForeCastResult(error = errorMsg)
                }
            }
        }
    }

    private fun convertMillToDate(milliSecond: Long): String {
        val milliDate = Date(milliSecond)
        val sdf =
            SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault())
        return sdf.format(milliDate)
    }

    fun setConnectivityLost(isLost: Boolean) {
        _isNetworkLost.value = isLost
    }

}