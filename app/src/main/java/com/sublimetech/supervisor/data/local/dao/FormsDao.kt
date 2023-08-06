package com.sublimetech.supervisor.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sublimetech.supervisor.domain.model.WomanForm.OfflineFormEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FormsDao {

    @Query("SELECT * FROM offline_forms")
    fun getForms(): Flow<List<OfflineFormEntity>>

    @Query("SELECT * FROM offline_forms WHERE formId =:id")
    fun getForm(id: String): OfflineFormEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForm(FormEntity: OfflineFormEntity)

    @Delete
    suspend fun deleteForm(FormEntity: OfflineFormEntity)

    @Update
    suspend fun updateForm(FormEntity: OfflineFormEntity)

}