package com.sublimetech.supervisor.data.repository.projects

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import com.sublimetech.supervisor.data.model.youtAndWoman.GroupDto
import com.sublimetech.supervisor.domain.repositories.professional.GroupRepository
import com.sublimetech.supervisor.presentation.utils.FirebasePaths.GROUPS
import com.sublimetech.supervisor.presentation.utils.FirebasePaths.PROGRAM
import com.sublimetech.supervisor.presentation.utils.FirebasePaths.PROJECTS
import javax.inject.Inject


class GroupRepositoryImpl @Inject constructor(
    val db: FirebaseFirestore
) : GroupRepository {

    private val baseRef = db.collection(PROJECTS)

    override fun getGroup(
        projectId: String,
        groupId: String,
        programType: String
    ): Task<DocumentSnapshot> {
        return baseRef
            .document(projectId)
            .collection(PROGRAM)
            .document(programType)
            .collection(GROUPS)
            .document(groupId)
            .get()
    }

    override fun getGroupList(projectId: String, programType: String): Task<QuerySnapshot> {
        return baseRef
            .document(projectId)
            .collection(PROGRAM)
            .document(programType)
            .collection(GROUPS)
            .get()
    }

    override fun getGroupListFromCache(
        projectId: String,
        programType: String
    ): Task<QuerySnapshot> {
        return baseRef
            .document(projectId)
            .collection(PROGRAM)
            .document(programType)
            .collection(GROUPS)
            .get(Source.CACHE) // Agrega esta línea para obtener la instantánea de caché
    }

    override fun createGroup(projectId: String, group: GroupDto, programType: String): Task<Void> {
        return baseRef
            .document(projectId)
            .collection(PROGRAM)
            .document(programType)
            .collection(GROUPS)
            .document(group.id)
            .set(group)
    }

    override fun getProfessionalGroupProgress(
        projectId: String,
        professionalId: String,
        programType: String
    ): Task<QuerySnapshot> {
        TODO("Not yet implemented")
    }


//    override fun getProfessionalGroupProgress(
//        projectId: String,
//        professionalId: String,
//    ): Task<QuerySnapshot> {
////        return baseRef
////            .document(projectId)
////            .collection(GROUPS)
////            .whereEqualTo("professionalId", professionalId)
////            .get()
//        TODO()
//
//    }


}