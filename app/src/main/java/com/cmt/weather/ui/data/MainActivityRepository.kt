package com.cmt.weather.ui.data

import com.cmt.weather.network.NetworkResult
import com.cmt.weather.ui.data.model.CityListRep
import com.cmt.weather.ui.data.model.CurrentInfoParser
import com.cmt.weather.ui.data.model.ForeCastInfoParser

class MainActivityRepository(private val dataSource: MainActivityDataSource) {

    fun loadCities(res: (NetworkResult<CityListRep>) -> Unit) {
        dataSource.loadCities { result ->
            res.invoke(result)
        }
    }

    fun fetchCurrentInfo(location: String, res: (NetworkResult<CurrentInfoParser>) -> Unit) {
        dataSource.fetchCurrentInfo(location) { result ->
            res.invoke(result)
        }
    }

    fun fetchForeCastInfo(locationId: String, res: (NetworkResult<ForeCastInfoParser>) -> Unit) {
        dataSource.fetchForeCastInfo(locationId) { result ->
            res.invoke(result)
        }
    }
}