package com.sublimetech.supervisor.data.repository.projects

import android.util.Log
import com.sublimetech.supervisor.data.local.dao.VisitDao
import com.sublimetech.supervisor.domain.model.VisitEntity
import com.sublimetech.supervisor.domain.repositories.professional.FamilyLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FamilyLocalRepositoryImpl @Inject constructor(
    val db: VisitDao
) : FamilyLocalRepository {

    override fun getVisits(): Flow<List<VisitEntity>> {
        Log.d("allVisits", db.getAllVisits().toString())
        return db.getAllVisits()
    }

    override suspend fun insertVisit(visit: VisitEntity) {
        db.insertVisit(visit)
    }

    override suspend fun updateVisit(visit: VisitEntity) {
        db.updateVisit(visit)
    }

    override suspend fun deleteVisit(visit: VisitEntity) {
        db.deleteVisit(visit)
    }
}