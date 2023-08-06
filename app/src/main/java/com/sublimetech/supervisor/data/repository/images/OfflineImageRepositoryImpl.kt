package com.sublimetech.supervisor.data.repository.images

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.sublimetech.supervisor.data.local.dao.FormsDao
import com.sublimetech.supervisor.domain.model.WomanForm.OfflineFormEntity
import com.sublimetech.supervisor.domain.repositories.images.OfflineFormsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineImageRepositoryImpl @Inject constructor(
    private val formsDao: FormsDao,
    private val context: Context,
) : OfflineFormsRepository {


    override fun getPendingForms(): Flow<List<OfflineFormEntity>> {
        return formsDao.getForms()
    }

    override fun getForm(id: String): OfflineFormEntity {
        return formsDao.getForm(id)
    }

    override suspend fun insertPendingForm(offlineFormEntity: OfflineFormEntity) {
        formsDao.insertForm(offlineFormEntity)
    }

    override suspend fun updatePendingForm(offlineFormEntity: OfflineFormEntity) {
        formsDao.updateForm(offlineFormEntity)
    }

    override suspend fun deleteOfflineForm(offlineFormEntity: OfflineFormEntity) {
        formsDao.deleteForm(offlineFormEntity)
    }


    override fun hasInternetConnectivity(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            val networkCapabilities = it.activeNetwork ?: return false
            val activeNetwork = it.getNetworkCapabilities(networkCapabilities) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
        return false
    }
}