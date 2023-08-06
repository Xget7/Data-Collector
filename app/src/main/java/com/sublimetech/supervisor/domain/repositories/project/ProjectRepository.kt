package com.sublimetech.supervisor.domain.repositories.project

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.sublimetech.supervisor.data.model.admin.ProjectDto

interface ProjectRepository {

    fun createProject(projectDto: ProjectDto): Task<Void>
    fun getProject(projectId: String): Task<DocumentSnapshot>

    fun getProjectsFromParticipant(participantId: String): Task<QuerySnapshot>


    fun updateProject(projectId: String, map: HashMap<String, Any>): Task<Void>


    fun getTownsById(professionalId: String): Query

    fun getTownById(townId: String): Task<DocumentSnapshot>
}