package com.sublimetech.supervisor.domain.useCases.projects

import android.util.Log
import com.sublimetech.supervisor.data.model.admin.ProjectDto
import com.sublimetech.supervisor.domain.repositories.project.ProjectRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.IOException
import javax.inject.Inject

class GetProjectUseCase @Inject constructor(
    private val repo: ProjectRepository
) {

    operator fun invoke(projectId: String): Flow<Result<ProjectDto>> = callbackFlow {
        Log.d("UseCase", "GetProjectUseCase")
        try {
            repo.getProject(projectId).addOnSuccessListener { i ->
                trySend(Result.success(i.toObject(ProjectDto::class.java) ?: ProjectDto()))
                close()
            }.addOnFailureListener { exception ->
                trySend(Result.failure(Throwable("Error creando obteniendo proyecto.")))
                Log.d("UseCase", "GetProjectUseCase $exception")
            }

            awaitClose {
                cancel()
                close()
            }
        } catch (e: IOException) {
            trySend(Result.failure(Throwable("Error conectando con la base de datos.")))
            Log.d("UseCase", "GetProjectUseCase $e")
        } catch (e: Exception) {
            trySend(Result.failure(e))

            Log.d("UseCase", "GetProjectUseCase $e")
        }
    }

}