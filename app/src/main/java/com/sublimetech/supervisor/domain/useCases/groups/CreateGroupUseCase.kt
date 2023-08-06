package com.sublimetech.supervisor.domain.useCases.groups

import android.util.Log
import com.sublimetech.supervisor.data.model.youtAndWoman.GroupDto
import com.sublimetech.supervisor.domain.repositories.professional.GroupRepository
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(
    private val repo: GroupRepository
) {

    suspend operator fun invoke(
        projectId: String,
        programType: String,
        group: GroupDto
    ): Result<Boolean> {
        Log.d("invoked", "GetGroupUseCase")
        var result: Result<Boolean>? = null
        try {
            repo.createGroup(projectId, group, programType).addOnSuccessListener {
                result = Result.success(true)
            }.addOnFailureListener { exception ->
                result = Result.failure(Throwable("Error obteniendo grupo."))
                Log.d("UseCase", "GetGroupListUseCase $exception")
            }.await()
        } catch (e: IOException) {
            result = Result.failure(Throwable("Error conectando con la base de datos."))
            Log.d("UseCase", "GetGroupListUseCase $e")
        } catch (e: Exception) {
            result = Result.failure(e)
            Log.d("UseCase", "GetGroupListUseCase $e")
        }

        return result ?: Result.failure(Throwable("Error en la base de datos"))
    }

}