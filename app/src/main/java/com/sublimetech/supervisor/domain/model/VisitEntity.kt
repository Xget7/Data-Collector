package com.sublimetech.supervisor.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sublimetech.supervisor.data.model.family.FamilyDto

@Entity(tableName = "visits")
data class VisitEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    var familyDto: FamilyDto
)