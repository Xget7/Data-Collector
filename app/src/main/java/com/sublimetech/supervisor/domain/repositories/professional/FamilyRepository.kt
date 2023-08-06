package com.sublimetech.supervisor.domain.repositories.professional

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.sublimetech.supervisor.data.model.family.FamilyDto
import com.sublimetech.supervisor.data.model.family.VisitDto

interface FamilyRepository {

    fun createFamily(projectId: String, family: FamilyDto): DocumentReference


    fun createVisit(projectId: String, visit: VisitDto): Task<Void>

    fun updateFamily(
        projectId: String,
        familyId: String,
        familyMap: HashMap<String, Any>
    ): DocumentReference

    fun updateVisits(
        projectId: String,
        visitsId: String,
        familyId: String,
        visitMap: HashMap<String, Any>
    ): Task<Void>

    fun getFamily(projectId: String, familyId: String): Task<DocumentSnapshot>
    fun getFamilies(projectId: String): Query

    fun getVisit(projectId: String, familyId: String): Task<QuerySnapshot>

}