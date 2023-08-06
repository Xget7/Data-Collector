package com.sublimetech.supervisor.data.repository.projects

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.sublimetech.supervisor.data.model.family.FamilyDto
import com.sublimetech.supervisor.data.model.family.VisitDto
import com.sublimetech.supervisor.domain.repositories.professional.FamilyRepository
import com.sublimetech.supervisor.presentation.utils.FirebasePaths.FAMILIES
import com.sublimetech.supervisor.presentation.utils.FirebasePaths.MEMBERS
import com.sublimetech.supervisor.presentation.utils.FirebasePaths.PROJECTS
import com.sublimetech.supervisor.presentation.utils.FirebasePaths.VISITS
import javax.inject.Inject


class FamilyRepositoryImpl @Inject constructor(
    val db: FirebaseFirestore
) : FamilyRepository {
    override fun createFamily(projectId: String, family: FamilyDto): DocumentReference {
        return db
            .collection(PROJECTS)
            .document(family.projectId)
            .collection(FAMILIES)
            .document(family.id)

    }


    override fun createVisit(projectId: String, visit: VisitDto): Task<Void> {
        return db
            .collection(PROJECTS)
            .document(projectId)
            .collection(FAMILIES)
            .document(visit.id)
            .collection(VISITS)
            .document(visit.id)
            .set(visit)
    }

    override fun updateFamily(
        projectId: String,
        familyId: String,
        familyMap: HashMap<String, Any>
    ): DocumentReference {
        return db
            .collection(PROJECTS)
            .document(projectId)
            .collection(FAMILIES)
            .document(familyId)

    }

    override fun updateVisits(
        projectId: String,
        visitsId: String,
        familyId: String,
        visitMap: HashMap<String, Any>
    ): Task<Void> {
        return db
            .collection(PROJECTS)
            .document(projectId)
            .collection(FAMILIES)
            .document(familyId)
            .collection(MEMBERS)
            .document(visitsId)
            .update(visitMap)
    }

    override fun getFamily(projectId: String, familyId: String): Task<DocumentSnapshot> {
        return db
            .collection(PROJECTS)
            .document(projectId)
            .collection(FAMILIES)
            .document(familyId)
            .get()
    }

    override fun getFamilies(projectId: String): Query {
        return db
            .collection(PROJECTS)
            .document(projectId)
            .collection(FAMILIES)
    }


    override fun getVisit(projectId: String, familyId: String): Task<QuerySnapshot> {
        return db
            .collection(PROJECTS)
            .document(projectId)
            .collection(FAMILIES)
            .document(familyId)
            .collection(VISITS)
            .get()
    }

}