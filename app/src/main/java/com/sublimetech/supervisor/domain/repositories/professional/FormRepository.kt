package com.sublimetech.supervisor.domain.repositories.professional

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.sublimetech.supervisor.data.model.youtAndWoman.FormDto

interface FormRepository {

    fun updateForm(
        projectId: String,
        programType: String,
        groupId: String,
        classId: String,
        form: HashMap<String, Any>
    ): DocumentReference

    fun createForm(
        projectId: String,
        programType: String,
        groupId: String,
        form: FormDto
    ): DocumentReference

    fun getForms(
        projectId: String,
        programType: String,
        groupId: String
    ): Task<QuerySnapshot>

    fun getFormsSnapshot(
        projectId: String,
        programType: String,
        groupId: String
    ): CollectionReference


    fun getFormById(
        projectId: String,
        programType: String,
        groupId: String,
        formId: String
    ): Task<DocumentSnapshot>


}