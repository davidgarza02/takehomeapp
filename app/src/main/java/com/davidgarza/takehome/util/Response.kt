package com.davidgarza.takehome.util

sealed class Response<T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Error<T>(val msg: String) : Response<T>()
}
