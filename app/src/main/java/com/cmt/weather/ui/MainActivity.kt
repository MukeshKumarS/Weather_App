package com.cmt.weather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cmt.weather.R
import com.cmt.weather.databinding.ActivityMainBinding
import com.cmt.weather.network.ConnectionDetector
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View Init
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val selectCity = activityMainBinding.cityDrop
        val tvMaxTemp = activityMainBinding.tvMaxTemp
        val tvMinTemp = activityMainBinding.tvMinTemp
        val tvWindPressure = activityMainBinding.tvWindPressure
        val tvHumidity = activityMainBinding.tvHumidity


        // ViewModel Init
        val activityViewModel = ViewModelProvider(this, MainActivityViewModelFactory())
            .get(MainActivityViewModel::class.java)

        // Loading city list initially
        activityViewModel.loadCities()

        // City List Event Trigger
        selectCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val cityName = selectCity.adapter.getItem(position).toString()
                val cityCountry = activityViewModel.getCityList.value?.get(position)?.country
                val isConnected = ConnectionDetector.isNetworkConnected(applicationContext)
                activityViewModel.setConnectivityLost(!isConnected)
                if (isConnected) {
                    activityViewModel.fetchCityCurrentInfo("$cityName,$cityCountry")
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        // City List loads in spinner after receiving from view model
        activityViewModel.getCityList.observe(this, Observer { cities ->
            val cityList = cities ?: return@Observer
            val strCityArr = activityViewModel.strGetCityList.value ?: ArrayList()
            val arrayAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, strCityArr)
            selectCity.adapter = arrayAdapter

            // for initial spinner value we are fetching the data of current weather info
            val location = selectCity.adapter.getItem(0).toString() + "," + cityList[0].country
            val isConnected = ConnectionDetector.isNetworkConnected(applicationContext)
            activityViewModel.setConnectivityLost(!isConnected)
            if (isConnected) {
                activityViewModel.fetchCityCurrentInfo(location)
            }
        })

        activityViewModel.isNetworkLost.observe(this, Observer { isLost ->
            val isNetLost = isLost ?: return@Observer
            if (isNetLost) {
                showToast("Network Lost, Please try again!")
            }
        })

        activityViewModel.fetchCurrentInfo.observe(this, Observer { response ->
            val result = response ?: return@Observer
            if (result.success != null) {
                activityViewModel.fetchForeCastInfo(result.success.cityId)
                tvMaxTemp.text = String.format(Locale.getDefault(), getString(R.string.max_temperature), result.success.maxTemp.toString())
                tvMinTemp.text = String.format(Locale.getDefault(), getString(R.string.min_temperature),result.success.minTemp.toString())
                tvWindPressure.text = String.format(Locale.getDefault(), getString(R.string.wind_pressure), result.success.windPressure.toString())
                tvHumidity.text = String.format(Locale.getDefault(), getString(R.string.humidity), result.success.humidity.toString())
            }
            if(result.error != null){
                showToast(result.error)
            }
        })

        activityViewModel.fetchForeCastResult.observe(this, Observer { response ->
            val result = response ?: return@Observer
            if (result.success != null) {
                Log.d("Test", result.success.cityId)
            }
            if(result.error != null){
                showToast(result.error)
            }
        })
    }

    private fun showToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}