package com.sublimetech.supervisor.data.repository.projects

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.QuerySnapshot
import com.sublimetech.supervisor.data.model.youtAndWoman.FormDto
import com.sublimetech.supervisor.domain.repositories.professional.FormRepository
import com.sublimetech.supervisor.presentation.utils.FirebasePaths
import com.sublimetech.supervisor.presentation.utils.FirebasePaths.FORMS
import com.sublimetech.supervisor.presentation.utils.FirebasePaths.GROUPS
import com.sublimetech.supervisor.presentation.utils.FirebasePaths.PROGRAM
import javax.inject.Inject

class FormRepositoryImpl @Inject constructor(
    val db: FirebaseFirestore
) : FormRepository {

    init {
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
            .build()
        db.firestoreSettings = settings
    }

    private val baseRef = db.collection(FirebasePaths.PROJECTS)
    override fun updateForm(
        projectId: String,
        programType: String,
        groupId: String,
        formId: String,
        form: HashMap<String, Any>
    ): DocumentReference {
        return baseRef
            .document(projectId)
            .collection(PROGRAM)
            .document(programType)
            .collection(GROUPS)
            .document(groupId)
            .collection(FORMS)
            .document(formId)

    }

    override fun createForm(
        projectId: String,
        programType: String,
        groupId: String,
        form: FormDto
    ): DocumentReference {
        return baseRef
            .document(projectId)
            .collection(PROGRAM)
            .document(programType)
            .collection(GROUPS)
            .document(groupId)
            .collection(FORMS)
            .document(form.id)

    }

    override fun getForms(
        projectId: String,
        programType: String,
        groupId: String
    ): Task<QuerySnapshot> {
        return baseRef
            .document(projectId)
            .collection(PROGRAM)
            .document(programType)
            .collection(GROUPS)
            .document(groupId)
            .collection(FORMS)
            .get()

    }

    override fun getFormsSnapshot(
        projectId: String,
        programType: String,
        groupId: String
    ): CollectionReference {
        return baseRef
            .document(projectId)
            .collection(PROGRAM)
            .document(programType)
            .collection(GROUPS)
            .document(groupId)
            .collection(FORMS)
    }

    override fun getFormById(
        projectId: String,
        programType: String,
        groupId: String,
        formId: String
    ): Task<DocumentSnapshot> {
        return baseRef
            .document(projectId)
            .collection(PROGRAM)
            .document(programType)
            .collection(GROUPS)
            .document(groupId)
            .collection(FORMS)
            .document(formId)
            .get()
    }


}
