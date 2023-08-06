package com.sublimetech.supervisor.domain.useCases.groups

import android.util.Log
import com.sublimetech.supervisor.data.model.youtAndWoman.FormDto
import com.sublimetech.supervisor.data.model.youtAndWoman.GroupDto
import com.sublimetech.supervisor.domain.repositories.professional.FormRepository
import com.sublimetech.supervisor.domain.repositories.professional.GroupRepository
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class GetProfessionalProgressUseCase @Inject constructor(
    private val repo: GroupRepository, private val formRepo: FormRepository

) {

    suspend operator fun invoke(
        projectId: String, groupId: String, programType: String
    ): Result<Float> {
        Log.d("invoked", "GetGroupUseCase")
        var result: Result<Float>? = null
        try {
            repo.getProfessionalGroupProgress(projectId, groupId, programType)
                .addOnSuccessListener { item ->
                    val groups = item.toObjects(GroupDto::class.java)
                    val goalsOfForms = groups.sumOf { it.formsGoal }
                    var numberOfLessons: Float = 0f

                    formRepo.getForms(projectId, programType, groupId)
                        .addOnSuccessListener { query ->
                            val classes = query.toObjects(FormDto::class.java)
                            numberOfLessons = classes.filter { it.finished }.size.toFloat()
                        }
                    val average: Float = numberOfLessons / goalsOfForms
                    result = Result.success(average)
                }.addOnFailureListener { exception ->
                    result = Result.failure(Throwable("Error obteniendo progreso."))
                    Log.d("UseCase", "GetGroupUseCase $exception")

                }.await()
        } catch (e: IOException) {
            result = Result.failure(Throwable("Error conectando con la base de datos."))
            Log.d("UseCase", "GetGroupUseCase $e")
        } catch (e: Exception) {
            Log.d("UseCase", "GetGroupUseCase $e")
        }

        return result ?: Result.failure(Throwable("Error en la base de datos"))
    }

}