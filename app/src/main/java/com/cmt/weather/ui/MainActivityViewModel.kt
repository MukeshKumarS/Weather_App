package com.cmt.weather.ui

import android.util.Log
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
    private var _isNetworkLost = MutableLiveData<Boolean>()
    var isNetworkLost: LiveData<Boolean> = _isNetworkLost

    fun loadCities() {
        activityRepository.loadCities{res ->
            if(res is NetworkResult.Success){
                val cityList = res.data.cityList
                val strCityArr = ArrayList<String>()
                for(city in cityList){
                    strCityArr.add(city.cityName)
                }
                _strGetCityList.value = strCityArr
                _getCityList.value = cityList
            }
        }
    }

    fun fetchCityCurrentInfo(location: String){
        activityRepository.fetchCurrentInfo(location) {response ->
            if(response is NetworkResult.Success) {
                Log.d("Test", response.data.city.cityName)
            }
        }
    }

    fun fetchForeCastInfo(locationId: Int){
        activityRepository.fetchForeCastInfo(locationId) {response ->
            if(response is NetworkResult.Success) {
                Log.d("Test", response.data.city.cityName)
            }
        }
    }

    fun convertMillToDate(milliSecond: Long): String {
        val milliDate = Date(milliSecond)
        val sdf =
            SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault())
        return sdf.format(milliDate)
    }

    fun setConnectivityLost(isLost: Boolean) {
        _isNetworkLost.value = isLost
    }

}