package com.sublimetech.supervisor.data.repository.admin

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.sublimetech.supervisor.data.model.admin.ProjectDto
import com.sublimetech.supervisor.domain.repositories.project.ProjectRepository
import com.sublimetech.supervisor.presentation.utils.FirebasePaths.PROJECTS
import com.sublimetech.supervisor.presentation.utils.FirebasePaths.TOWNS
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : ProjectRepository {


    override fun createProject(projectDto: ProjectDto): Task<Void> {
        return db
            .collection(PROJECTS)
            .document(projectDto.id)
            .set(projectDto)
    }

    override fun getProject(projectId: String): Task<DocumentSnapshot> {
        return db
            .collection(PROJECTS)
            .document(projectId)
            .get()
    }

    override fun getProjectsFromParticipant(participantId: String): Task<QuerySnapshot> {
        return db
            .collection(PROJECTS)
            .whereArrayContains("professionals", participantId)
            .get()
    }


    override fun updateProject(projectId: String, map: HashMap<String, Any>): Task<Void> {
        return db
            .collection(PROJECTS)
            .document(projectId)
            .update(map)
    }

    override fun getTownsById(professionalId: String): Query {
        return db
            .collection(TOWNS)
            .whereArrayContains("professionalsId", professionalId)

    }

    override fun getTownById(townId: String): Task<DocumentSnapshot> {
        return db
            .collection(TOWNS)
            .document(townId)
            .get()
    }

}