package com.sublimetech.supervisor.data.repository.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.sublimetech.supervisor.domain.repositories.network.ConnectivityInterface
import javax.inject.Inject

class ConnectivityImpl @Inject constructor(
    private val application: Context
) : ConnectivityInterface {


    override fun isOnline(): Boolean {
        val connectivityManager = application.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        var res = false
        connectivityManager.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                res = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
        return res
    }

}