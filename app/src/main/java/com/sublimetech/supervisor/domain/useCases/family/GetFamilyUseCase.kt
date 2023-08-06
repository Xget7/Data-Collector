package com.sublimetech.supervisor.domain.useCases.family

import android.util.Log
import com.sublimetech.supervisor.data.model.family.FamilyDto
import com.sublimetech.supervisor.domain.repositories.professional.FamilyRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.io.IOException
import javax.inject.Inject

class GetFamilyUseCase @Inject constructor(
    private val repo: FamilyRepository
) {

    operator fun invoke(projectId: String, familyId: String) = callbackFlow<Result<FamilyDto>> {
        try {
            repo.getFamily(projectId, familyId).addOnSuccessListener { i ->
                trySend(Result.success(i.toObject(FamilyDto::class.java) ?: FamilyDto()))
            }.addOnFailureListener { exception ->
                trySend(Result.failure(Throwable("Error obteniendo datos de familia.")))
                Log.d("UseCase", "GetFamilyUseCase $exception")
            }

            awaitClose {
                close()
                cancel()
            }

        } catch (e: IOException) {
            trySend(Result.failure(Throwable("Error conectando con la base de datos.")))
        } catch (e: Exception) {
            trySend(Result.failure(Throwable("Error inesperado.")))
        }
    }

}

