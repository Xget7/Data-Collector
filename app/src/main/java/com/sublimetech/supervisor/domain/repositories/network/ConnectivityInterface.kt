package com.sublimetech.supervisor.domain.repositories.network

interface ConnectivityInterface {

    fun isOnline(): Boolean
}