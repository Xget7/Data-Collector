package com.sublimetech.supervisor.domain.useCases.family

import android.util.Log
import com.sublimetech.supervisor.data.model.family.FamilyDto
import com.sublimetech.supervisor.domain.repositories.professional.FamilyRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.io.IOException
import javax.inject.Inject

class GetFamiliesUseCase @Inject constructor(
    private val repo: FamilyRepository
) {

    operator fun invoke(projectId: String) = callbackFlow<Result<List<FamilyDto>>> {
        try {
            val callback = repo.getFamilies(projectId).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    trySend(Result.failure(Throwable("Error creando familia.")))
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val families = snapshot.toObjects(FamilyDto::class.java)
                    trySend(Result.success(families))
                } else {
                    trySend(Result.success(emptyList()))
                }
            }
            awaitClose {
                callback.remove()
                cancel()
                close()
            }

        } catch (e: IOException) {
            trySend(Result.failure(Throwable("Error conectando con la base de datos.")))
        } catch (e: Exception) {
            trySend(Result.failure(Throwable("Error Inesperado.")))
            close()
            Log.d("CreateFamilyUseCase", "CreateFamilyUseCase $e")
        }
    }

}