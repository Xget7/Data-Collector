package com.sublimetech.supervisor.domain.useCases.forms

import android.util.Log
import com.sublimetech.supervisor.data.model.youtAndWoman.FormDto
import com.sublimetech.supervisor.domain.model.youthAndWoman.Form
import com.sublimetech.supervisor.domain.repositories.professional.FormRepository
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class GetFormByIdUseCase @Inject constructor(
    private val repo: FormRepository
) {

    suspend operator fun invoke(
        projectId: String,
        programType: String,
        groupId: String,
        formId: String
    ): Result<Form> {
        var result: Result<Form>? = null
        try {
            repo.getFormById(projectId, programType, groupId, formId).addOnSuccessListener {
                result = Result.success(it.toObject(FormDto::class.java)?.toForm()!!)
            }.addOnFailureListener { exception ->
                result = Result.failure(Throwable("Error obteniendo formato."))
                Log.d("UseCase", "UpdateGroupClassesUseCase $exception")
            }.await()
        } catch (e: IOException) {
            result = Result.failure(Throwable("Error conectando con la base de datos."))
            Log.d("UseCase", "UpdateGroupClassesUseCase $e")
        } catch (e: Exception) {
            result = Result.failure(e)
            Log.d("UseCase", "UpdateGroupClassesUseCase $e")
        }

        return result ?: Result.failure(Throwable("Error en la base de datos GET FORM USE CASE"))
    }

}

class CheckFormExist @Inject constructor(
    private val repo: FormRepository
) {

    suspend operator fun invoke(
        projectId: String,
        programType: String,
        groupId: String,
        formId: String
    ): Boolean {
        var result: Boolean = false
        try {
            repo.getFormById(projectId, programType, groupId, formId).addOnSuccessListener {
                result = it.exists()
            }.addOnFailureListener { exception ->
                result = false
                Log.d("UseCase", "UpdateGroupClassesUseCase $exception")
            }.await()
        } catch (e: IOException) {
            result = false
            Log.d("UseCase", "UpdateGroupClassesUseCase $e")
        } catch (e: Exception) {
            result = false
            Log.d("UseCase", "UpdateGroupClassesUseCase $e")
        }

        return result
    }

}