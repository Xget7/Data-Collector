package com.sublimetech.supervisor.domain.useCases.projects

import com.sublimetech.supervisor.data.model.admin.ProjectDto
import com.sublimetech.supervisor.domain.repositories.project.ProjectRepository
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class CreateProjectUseCase @Inject constructor(
    private val repo: ProjectRepository
) {

    suspend operator fun invoke(project: ProjectDto): Result<Boolean> {
        var result: Result<Boolean>? = null
        try {
            repo.createProject(project).addOnSuccessListener { i ->
                Result.success(true)
            }.addOnFailureListener { exception ->
                result = Result.failure(Throwable("Error creando proyecto."))
            }.await()
        } catch (e: IOException) {
            result = Result.failure(Throwable("Error conectando con la base de datos."))
        }
        return result ?: Result.failure(Throwable("Error en la base de datos"))
    }

}