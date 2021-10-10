package com.cmt.weather.ui.data

import com.cmt.weather.network.NetworkResult
import com.cmt.weather.ui.data.model.CityListRep
import com.cmt.weather.ui.data.model.CurrentInfoParser
import com.cmt.weather.ui.data.model.ForeCastInfoParser
import com.cmt.weather.ui.data.model.HistoryInfoParser

// Currently we are using repository for only network call
// Actually this is where we can fetch from Local storage / network based on the network availability
class MainActivityRepository(private val dataSource: MainActivityDataSource) {

    // To maintain the source for data we are getting city data similar to network api methods like below
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

    fun fetchHistoryInfo(locationId: String, res: (NetworkResult<HistoryInfoParser>) -> Unit) {
        dataSource.fetchHistoryInfo(locationId) { result ->
            res.invoke(result)
        }
    }
}