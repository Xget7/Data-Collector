package com.sublimetech.supervisor.domain.useCases.groups

import android.util.Log
import com.sublimetech.supervisor.data.model.youtAndWoman.GroupDto
import com.sublimetech.supervisor.domain.repositories.professional.GroupRepository
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class GetGroupListUseCase @Inject constructor(
    private val repo: GroupRepository
) {

    suspend operator fun invoke(projectId: String, programType: String): Result<List<GroupDto>> {
        Log.d("invoked", "GetGroupUseCase")
        var result: Result<List<GroupDto>>? = null
        try {
            repo.getGroupList(projectId, programType).addOnSuccessListener { i ->
                result = Result.success(i.toObjects(GroupDto::class.java))
                Log.d("UseCase", " GetGroupListUseCase = ${i.documents}")
            }.addOnFailureListener { exception ->
                result = Result.failure(Throwable("Error obteniendo grupo."))
                Log.d("UseCase", "GetGroupListUseCase $exception")
            }.await()
        } catch (e: IOException) {
            result = Result.failure(Throwable("Error conectando con la base de datos."))
            Log.d("UseCase", "GetGroupListUseCase $e")
        } catch (e: Exception) {
            result = Result.failure(Throwable("Error."))
            Log.d("UseCase", "GetGroupListUseCase $e")
        }

        return result ?: Result.failure(Throwable("Error en la base de datos GROUP LIST USE CASE"))
    }


}

