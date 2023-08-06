package com.sublimetech.supervisor.domain.useCases.family

import android.util.Log
import com.sublimetech.supervisor.data.model.family.FamilyDto
import com.sublimetech.supervisor.domain.repositories.professional.FamilyRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.IOException
import javax.inject.Inject

class CreateFamilyUseCase @Inject constructor(
    private val repo: FamilyRepository
) {

    operator fun invoke(family: FamilyDto): Flow<Result<Boolean>> = callbackFlow {
        try {
            val ref = repo.createFamily(family.projectId, family)

            ref.set(family).addOnSuccessListener {
                trySend(Result.success(true))
                cancel()
            }.addOnFailureListener { exception ->
                trySend(Result.failure(Throwable("Error creando familia.")))
                Log.d("UseCase", "CreateFamilyUseCase $exception")
            }
            val callback = ref.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    trySend(Result.failure(Throwable("Error creando familia.")))
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    trySend(Result.success(true))
                } else {
                    trySend(Result.failure(Throwable("Error creando familia.")))
                }
            }
            awaitClose {
                callback.remove()
                cancel()
                close()
            }
        } catch (e: IOException) {
            trySend(Result.failure(Throwable("Error conectando con la base de datos.")))
            Log.d("UseCase", "CreateFormUseCase $e")
        } catch (e: Exception) {
            trySend(Result.failure(e))
            Log.d("UseCase", "CreateFormUseCase $e")
        }

    }
}