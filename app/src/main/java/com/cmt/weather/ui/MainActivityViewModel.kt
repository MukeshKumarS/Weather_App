package com.cmt.weather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.weather.network.NetworkResult
import com.cmt.weather.ui.data.MainActivityRepository
import com.cmt.weather.ui.data.model.CitiesParser
import com.cmt.weather.ui.data.model.HistoryInfoParser
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class MainActivityViewModel(val activityRepository: MainActivityRepository) : ViewModel() {
    // City List Data
    private var _getCityList = MutableLiveData<List<CitiesParser>>()
    var getCityList: LiveData<List<CitiesParser>> = _getCityList

    // City Name Array List
    private var _strGetCityList = MutableLiveData<List<String>>()
    var strGetCityList: LiveData<List<String>> = _strGetCityList

    // Current Weather Result
    private var _fetchCurrentInfo = MutableLiveData<CurrentInfoResult>()
    var fetchCurrentInfo: LiveData<CurrentInfoResult> = _fetchCurrentInfo

    // Forecast Result based on selection
    private var _fetchForeCastResult = MutableLiveData<ForeCastResult>()
    var fetchForeCastResult: LiveData<ForeCastResult> = _fetchForeCastResult

    // History Result
    private var _fetchHistoryResult = MutableLiveData<HistoryResult>()
    var fetchHistoryResult: LiveData<HistoryResult> = _fetchHistoryResult

    // Network Check Getter
    private var _isNetworkLost = MutableLiveData<Boolean>()
    var isNetworkLost: LiveData<Boolean> = _isNetworkLost

    // Fetching City data from JSON string
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

    // Fetching Weather condition based on Specific City Name
    fun fetchCityCurrentInfo(location: String) {
        activityRepository.fetchCurrentInfo(location) { response ->
            if (response is NetworkResult.Success) {
                val currentInfo = response.data
                val weatherList = currentInfo.weatherData
                var strWeather = ""
                for (weather in weatherList) {
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
                    if (error.message?.isNotEmpty() == true)
                        _fetchCurrentInfo.value =
                            CurrentInfoResult(error = error.message ?: "Please try again")
                    else
                        _fetchCurrentInfo.value = CurrentInfoResult(error = errorMsg)
                }
            }
        }
    }

    // Fetching Forecast condition based on Specific City Id
    fun fetchForeCastInfo(locationId: String) {
        activityRepository.fetchForeCastInfo(locationId) { response ->
            if (response is NetworkResult.Success) {
                val currentInfo = response.data
                val weatherDataList = currentInfo.weatherData
                val foreCastArrayList = ArrayList<ForeCastResultView>()
                for (foreCastList in weatherDataList) {
                    val weatherList =
                        foreCastList.weatherList  // Looping all weather condition and Displaying
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
                    if (error.message?.isNotEmpty() == true)
                        _fetchForeCastResult.value =
                            ForeCastResult(error = error.message ?: "Please try again")
                    else
                        _fetchForeCastResult.value = ForeCastResult(error = errorMsg)
                }
            }
        }
    }

    // Fetching Forecast condition based on Specific City Id
    fun fetchHistoryInfo(date: String, locationId: String) {
        activityRepository.fetchHistoryInfo(locationId) { response ->
            if (response is NetworkResult.Success) {
                val toCal = Calendar.getInstance(Locale.getDefault())
                val fromCal = Calendar.getInstance(Locale.getDefault())
                val cDate = convertStringToDate(date)
                val dates = ArrayList<Long>()
                if (cDate != null) {
                    toCal.time = cDate
                    fromCal.time = cDate
                    fromCal.add(Calendar.DATE, -29)
                    while (!fromCal.after(toCal)) {
                        val rDate: Date = fromCal.time
                        dates.add(rDate.time)
                        fromCal.add(Calendar.DATE, 1)
                    }
                }
                val currentInfo = response.data
                val historyDataList = currentInfo.weatherData
                val rLen = historyDataList.size
                val wDataList: ArrayList<HistoryInfoParser.CityWeather> = ArrayList()
                for (rDate in dates) {
                    val random = Random.nextInt(rLen)
                    val resultView = historyDataList[random]
                    wDataList.add(resultView)
                }
                val historyArrayList = ArrayList<HistoryResultView>()
                val wDLen = wDataList.size - 1
                for (j in 0..wDLen) {
                    val hData = wDataList[j]
                    val weatherList =
                        hData.weatherList // Looping all weather condition and Displaying
                    var strWeather = ""
                    for (weather in weatherList) {
                        strWeather += "${weather.main}, "
                    }
                    strWeather = strWeather.substring(0, strWeather.length - 2)
                    val recursiveDates = convertMillToDate(dates[j])
                    val historyResultView = HistoryResultView(
                        cityName = currentInfo.city.cityName,
                        cityId = currentInfo.city.geoNameId.toString(),
                        weather = strWeather,
                        date = recursiveDates,
                        maxTemp = hData.temp.max,
                        minTemp = hData.temp.min,
                        dayTemp = hData.temp.day,
                        mornTemp = hData.temp.morn,
                        eveTemp = hData.temp.eve,
                        nightTemp = hData.temp.night,
                        windPressure = hData.pressure,
                        humidity = hData.humidity
                    )
                    historyArrayList.add(historyResultView)
                }
                _fetchHistoryResult.value = HistoryResult(success = historyArrayList)
            } else {
                if (response is NetworkResult.Error) {
                    val error = response.exception
                    val errorMsg = "Please try again!"
                    if (error.message?.isNotEmpty() == true)
                        _fetchHistoryResult.value =
                            HistoryResult(error = error.message ?: "Please try again")
                    else
                        _fetchHistoryResult.value = HistoryResult(error = errorMsg)
                }
            }
        }
    }

    // used for converting the milliseconds to required date format
    private fun convertMillToDate(milliSecond: Long): String {
        val milliDate = Date(milliSecond)
        val sdf =
            SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault())
        return sdf.format(milliDate)
    }


    // used for converting the date string to required date format
    private fun convertStringToDate(dateString: String): Date? {
        val sdf =
            SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault())
        return sdf.parse(dateString)
    }

    // network connectivity status
    fun setConnectivityLost(isLost: Boolean) {
        _isNetworkLost.value = isLost
    }

}