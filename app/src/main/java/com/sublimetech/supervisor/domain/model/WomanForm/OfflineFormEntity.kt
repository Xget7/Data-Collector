package com.sublimetech.supervisor.domain.model.WomanForm

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sublimetech.supervisor.domain.model.youthAndWoman.Form

@Entity(tableName = "offline_forms")
data class OfflineFormEntity(
    @PrimaryKey(autoGenerate = false)
    val formId: String,
    val groupId: String,

    val projectId: String,
    val programType: String,
    var formWithUris: Form
)