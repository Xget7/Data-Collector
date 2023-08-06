package com.sublimetech.supervisor.domain.useCases.groups

import android.util.Log
import com.sublimetech.supervisor.data.model.youtAndWoman.GroupDto
import com.sublimetech.supervisor.domain.repositories.professional.GroupRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.IOException
import javax.inject.Inject

class GetGroupUseCase @Inject constructor(
    private val repo: GroupRepository
) {

    operator fun invoke(
        projectId: String,
        groupId: String,
        programType: String
    ): Flow<Result<GroupDto>> = callbackFlow {
        Log.d("invoked", "GetGroupUseCase")
        Log.d("GetGroupUseCase", projectId + groupId + programType)
        try {
            repo.getGroup(projectId, groupId, programType).addOnSuccessListener { i ->
                trySend(Result.success(i.toObject(GroupDto::class.java) ?: GroupDto()))
                Log.d("GetGroupUseCase", i.toObject(GroupDto::class.java).toString())
                close()
            }.addOnFailureListener { exception ->
                trySend(Result.success(GroupDto()))
            }

            awaitClose {
                close()
            }
        } catch (e: IOException) {
            trySend(Result.failure(Throwable("Error conectando con la base de datos.")))
            Log.d("UseCase", "GetGroupUseCase $e")
        } catch (e: Exception) {
            trySend(Result.failure(Throwable("Error inesperado.")))

            Log.d("UseCase", "GetGroupUseCase $e")
        }
    }

}