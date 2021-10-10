package com.cmt.weather.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmt.weather.R
import com.cmt.weather.databinding.ActivityMainBinding
import com.cmt.weather.network.ConnectionDetector
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // View Init
        val selectCity = activityMainBinding.cityDrop // City Drop Down
        val tvCurrentDate = activityMainBinding.tvCurrentDate // Date
        val tvWeatherCondition = activityMainBinding.tvWeatherCondition // Weather conditions looped and concatenated
        val tvMaxTemp = activityMainBinding.tvMaxTemp
        val tvMinTemp = activityMainBinding.tvMinTemp
        val tvWindPressure = activityMainBinding.tvWindPressure
        val tvHumidity = activityMainBinding.tvHumidity
        val tvForeCastLabel = activityMainBinding.tvForeCastLabel
        val tvHistoryLabel = activityMainBinding.tvHistoryLabel
        val progressContainer = activityMainBinding.progressContainer
        val rvForeCast = activityMainBinding.rvForeCast
        val horizontalForeCast = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        rvForeCast.layoutManager = horizontalForeCast
        val rvHistory = activityMainBinding.rvHistory
        val horizontalHistory = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        rvHistory.layoutManager = horizontalHistory

        // ViewModel Init
        val activityViewModel = ViewModelProvider(this, MainActivityViewModelFactory())
            .get(MainActivityViewModel::class.java)

        // Loading city list initially
        progressContainer.visibility = View.VISIBLE
        activityViewModel.loadCities()

        // City List Event Trigger
        selectCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val cityName = selectCity.adapter.getItem(position).toString()
                val cityCountry = activityViewModel.getCityList.value?.get(position)?.country
                val isConnected = ConnectionDetector.isNetworkConnected(applicationContext)
                activityViewModel.setConnectivityLost(!isConnected)
                if (isConnected) {
                    progressContainer.visibility = View.VISIBLE
                    activityViewModel.fetchCityCurrentInfo("$cityName,$cityCountry")
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        // City List loads in spinner after receiving from view model
        activityViewModel.getCityList.observe(this, Observer { cities ->
            progressContainer.visibility = View.GONE
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
                progressContainer.visibility = View.VISIBLE
                activityViewModel.fetchCityCurrentInfo(location)
            }
        })

        // Network Lost Toast
        activityViewModel.isNetworkLost.observe(this, Observer { isLost ->
            val isNetLost = isLost ?: return@Observer
            if (isNetLost) {
                showToast("Network Lost, Please try again!")
            }
        })

        // City List Summary Load & Fetching Forecast based on City Id
        activityViewModel.fetchCurrentInfo.observe(this, Observer { response ->
            val result = response ?: return@Observer
            if (result.success != null) {

                // Fetching Forecast based on City Id
                activityViewModel.fetchForeCastInfo(result.success.cityId)

                // Fetching History based on City Id
                activityViewModel.fetchHistoryInfo(result.success.date, result.success.cityId)

                // Loading Current API data in views
                tvWeatherCondition.text = String.format(
                    Locale.getDefault(),
                    getString(R.string.weather_condition_s),
                    result.success.weather
                )
                tvCurrentDate.text = String.format(
                    Locale.getDefault(),
                    getString(R.string.current_date_s),
                    result.success.date
                )
                tvMaxTemp.text = String.format(
                    Locale.getDefault(),
                    getString(R.string.max_temperature),
                    result.success.maxTemp.toString()
                )
                tvMinTemp.text = String.format(
                    Locale.getDefault(),
                    getString(R.string.min_temperature),
                    result.success.minTemp.toString()
                )
                tvWindPressure.text = String.format(
                    Locale.getDefault(),
                    getString(R.string.wind_pressure),
                    result.success.windPressure.toString()
                )
                tvHumidity.text = String.format(
                    Locale.getDefault(),
                    getString(R.string.humidity),
                    result.success.humidity.toString()
                )
            }
            if (result.error != null) {
                progressContainer.visibility = View.GONE
                showToast(result.error)
            }
        })

        // Fetched Forecast data obtained from API
        activityViewModel.fetchForeCastResult.observe(this, Observer { response ->
            val result = response ?: return@Observer
            if (result.success != null) {
                val foreCastList = result.success
                if(foreCastList.isEmpty()){
                    tvForeCastLabel.visibility = View.GONE
                    rvForeCast.visibility = View.GONE
                } else {
                    tvForeCastLabel.visibility = View.VISIBLE
                    rvForeCast.visibility = View.VISIBLE
                    val foreCastAdapter = ForeCastAdapter(this, foreCastList)
                    rvForeCast.adapter = foreCastAdapter
                }
            } else {
                tvForeCastLabel.visibility = View.GONE
                rvForeCast.visibility = View.GONE
            }

            if (result.error != null) {
                progressContainer.visibility = View.GONE
                showToast(result.error)
            }
        })

        // Fetched Forecast data obtained from API
        activityViewModel.fetchHistoryResult.observe(this, Observer { response ->
            progressContainer.visibility = View.GONE
            val result = response ?: return@Observer
            if (result.success != null) {
                val historyResult = result.success
                if(historyResult.isEmpty()){
                    tvHistoryLabel.visibility = View.GONE
                    rvHistory.visibility = View.GONE
                } else {
                    tvHistoryLabel.visibility = View.VISIBLE
                    rvHistory.visibility = View.VISIBLE
                    val historyAdapter = HistoryAdapter(this, historyResult)
                    rvHistory.adapter = historyAdapter
                }
            } else {
                tvHistoryLabel.visibility = View.GONE
                rvHistory.visibility = View.GONE
            }

            if (result.error != null) {
                progressContainer.visibility = View.GONE
                showToast(result.error)
            }
        })

    }


    private fun showToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}