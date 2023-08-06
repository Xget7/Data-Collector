package com.sublimetech.supervisor.domain.repositories.professional

import com.sublimetech.supervisor.domain.model.VisitEntity
import kotlinx.coroutines.flow.Flow

interface FamilyLocalRepository {
    fun getVisits(): Flow<List<VisitEntity>>

    suspend fun insertVisit(visit: VisitEntity)
    suspend fun updateVisit(visit: VisitEntity)

    suspend fun deleteVisit(visit: VisitEntity)
}