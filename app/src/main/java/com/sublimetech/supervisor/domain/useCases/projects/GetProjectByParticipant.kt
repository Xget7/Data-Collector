package com.sublimetech.supervisor.domain.useCases.projects

import android.util.Log
import com.sublimetech.supervisor.data.model.admin.ProjectDto
import com.sublimetech.supervisor.domain.repositories.project.ProjectRepository
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class GetProjectByParticipant @Inject constructor(
    private val repo: ProjectRepository
) {

    suspend operator fun invoke(userId: String): Result<List<ProjectDto>> {
        var result: Result<List<ProjectDto>>? = null
        try {
            repo.getProjectsFromParticipant(userId).addOnSuccessListener { i ->
                result = Result.success(i.toObjects(ProjectDto::class.java))
            }.addOnFailureListener { exception ->
                result = Result.failure(Throwable("Error obteniendo proyectos."))
                Log.d("UseCase", "GetProjectUseCase $exception")

            }.await()
        } catch (e: IOException) {
            result = Result.failure(Throwable("Error conectando con la base de datos."))
            Log.d("UseCase", "GetProjectUseCase $e")
        } catch (e: Exception) {
            Log.d("UseCase", "GetProjectUseCase $e")
        }
        return result
            ?: Result.failure(Throwable("Error en la base de datos GET PROJECT PARTICIPANT"))
    }

}