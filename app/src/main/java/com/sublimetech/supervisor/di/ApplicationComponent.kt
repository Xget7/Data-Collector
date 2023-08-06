package com.sublimetech.supervisor.di

import com.sublimetech.supervisor.SupervisorApp
import dagger.Component


@Component(
    modules =
    [FirebaseModule::class, AppModule::class, RoomModule::class] //BroadcastReceiverModule::class]
)
interface ApplicationComponent {

    fun inject(app: SupervisorApp)
}