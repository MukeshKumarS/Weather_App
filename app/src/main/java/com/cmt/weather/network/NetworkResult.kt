package com.cmt.weather.network

import com.cmt.weather.ui.data.model.CurrentInfoParser

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class NetworkResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : NetworkResult<T>()
    data class Error(val exception: Exception) : NetworkResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}