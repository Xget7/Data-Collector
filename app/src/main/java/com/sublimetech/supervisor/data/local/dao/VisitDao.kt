package com.sublimetech.supervisor.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sublimetech.supervisor.domain.model.VisitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VisitDao {

    @Query("SELECT * FROM visits")
    fun getAllVisits(): Flow<List<VisitEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVisit(visit: VisitEntity)

    @Delete
    suspend fun deleteVisit(visit: VisitEntity)

    @Update
    suspend fun updateVisit(visit: VisitEntity)
}