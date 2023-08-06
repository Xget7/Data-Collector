package com.sublimetech.supervisor

import OfflineUpdatesReceiver
import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.sublimetech.supervisor.di.DaggerApplicationComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SupervisorApp : Application() {

    val appComponent = DaggerApplicationComponent
        .create()

    val receiver = OfflineUpdatesReceiver()

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        registerReceiver(
            receiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )


    }

}