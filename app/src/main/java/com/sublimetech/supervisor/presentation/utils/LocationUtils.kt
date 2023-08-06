package com.sublimetech.supervisor.presentation.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class LocationUtils(appContext: Context) {
    private val mLocationRequest = LocationRequest.create()

    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(appContext)

    init {
        mLocationRequest.interval = 60000
        mLocationRequest.fastestInterval = 5000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    @SuppressLint("MissingPermission")
    suspend fun getLastKnownLocation(): Result<Location>? {
        return withContext(Main) {
            var result: Result<Location>? = null
            fusedLocationClient.getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                        CancellationTokenSource().token

                    override fun isCancellationRequested() = false
                })
                .addOnSuccessListener { location: Location? ->
                    if (location == null)
                        result = Result.failure(Throwable("No se pudo obtener la localizacion"))
                    else {
                        result = Result.success(location)
                    }

                }.await()
            result
        }
    }

}
