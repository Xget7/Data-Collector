package com.sublimetech.supervisor.domain.repositories.images

import com.sublimetech.supervisor.domain.model.WomanForm.OfflineFormEntity
import kotlinx.coroutines.flow.Flow

interface OfflineFormsRepository {
    fun hasInternetConnectivity(): Boolean

    fun getPendingForms(): Flow<List<OfflineFormEntity>>
    fun getForm(id: String): OfflineFormEntity

    suspend fun insertPendingForm(offlineFormEntity: OfflineFormEntity)
    suspend fun updatePendingForm(offlineFormEntity: OfflineFormEntity)

    suspend fun deleteOfflineForm(offlineFormEntity: OfflineFormEntity)

}