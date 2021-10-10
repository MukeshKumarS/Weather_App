package com.cmt.weather.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

class ConnectionDetector {

    companion object {

        fun isNetworkConnected(context: Context): Boolean {

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return isConnected(actNw)
        }

        private fun isConnected(networkCapabilities: NetworkCapabilities?): Boolean {
            return when (networkCapabilities) {
                null -> false
                else -> with(networkCapabilities) {
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI
                    )
                }
            }
        }
    }
}
