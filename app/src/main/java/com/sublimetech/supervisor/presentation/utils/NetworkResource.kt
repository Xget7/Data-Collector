package com.sublimetech.supervisor.presentation.utils

sealed class NetworkResource<T>(
    val data: T? = null,
    val message: String? = null,
    val loading: Long? = null
) {
    class Success<T>(data: T) : NetworkResource<T>(data)
    class Error<T>(message: String, data: T? = null) : NetworkResource<T>(data, message)
    class Loading<T>(loading: Long? = null) : NetworkResource<T>(null, null, loading)
}
