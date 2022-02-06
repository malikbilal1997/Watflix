package com.phoenixdevelopers.watflix.utils

sealed class Response<out T> {

    object Loading : Response<Nothing>()

    data class Error(val throwable: Throwable) : Response<Nothing>()

    data class Success<T>(val item: T) : Response<T>()
}
