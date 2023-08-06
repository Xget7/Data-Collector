package com.sublimetech.supervisor.domain.repositories.professional

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.sublimetech.supervisor.data.model.youtAndWoman.GroupDto

interface GroupRepository {

    fun getGroup(projectId: String, groupId: String, programType: String): Task<DocumentSnapshot>

    fun getGroupList(projectId: String, programType: String): Task<QuerySnapshot>
    fun getGroupListFromCache(projectId: String, programType: String): Task<QuerySnapshot>
    fun createGroup(projectId: String, group: GroupDto, programType: String): Task<Void>

    fun getProfessionalGroupProgress(
        projectId: String,
        professionalId: String,
        programType: String
    ): Task<QuerySnapshot>


}