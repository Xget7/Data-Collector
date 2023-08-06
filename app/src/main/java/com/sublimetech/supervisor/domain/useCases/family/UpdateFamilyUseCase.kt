package com.sublimetech.supervisor.domain.useCases.family

import android.util.Log
import com.sublimetech.supervisor.domain.repositories.professional.FamilyRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.io.IOException
import javax.inject.Inject

class UpdateFamilyUseCase @Inject constructor(
    private val repo: FamilyRepository
) {

    operator fun invoke(projectId: String, familyId: String, familyMap: HashMap<String, Any>) =
        callbackFlow<Result<Boolean>> {
            try {
                val ref = repo.updateFamily(projectId, familyId, familyMap)

                ref.update(familyMap).addOnSuccessListener {
                    trySend(Result.success(true))
                }.addOnFailureListener {
                    trySend(Result.failure(Throwable("Error actualizando formulario")))
                }
                val callback = ref.addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        trySend(Result.failure(Throwable("Error actualizando formulario")))
                        return@addSnapshotListener
                    }
                    val source = if (snapshot != null && snapshot.metadata.hasPendingWrites())
                        "Local"
                    else
                        "Server"
                    if (snapshot != null && snapshot.exists()) {
                        trySend(Result.success(true))
                        Log.d("UpdateFormUseCase", "$source data: g o d ")

                    } else {
                        trySend(Result.failure(Throwable("Error actualizando formulario")))
                        Log.d("UpdateFormUseCase", "$source data: null")
                    }
                }
                awaitClose {
                    callback.remove()
                    cancel()
                    close()

                }

            } catch (e: IOException) {
                trySend(Result.failure(Throwable("Error conectando con base de datos.")))
                Log.d("IOEXCEPTIONMM", e.toString())
            } catch (e: Exception) {
                trySend(Result.failure(Throwable("Error Inesperado.")))
                Log.d("IOEXCEPTIONNNN", e.toString())

            }
        }
}