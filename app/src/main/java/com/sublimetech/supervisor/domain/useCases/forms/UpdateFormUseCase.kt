package com.sublimetech.supervisor.domain.useCases.forms

import android.util.Log
import com.sublimetech.supervisor.domain.repositories.professional.FormRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.IOException
import javax.inject.Inject

class UpdateFormUseCase @Inject constructor(
    private val repo: FormRepository
) {

    operator fun invoke(
        projectId: String,
        programType: String,
        groupId: String,
        formId: String,
        form: HashMap<String, Any>
    ): Flow<Result<Boolean>> = callbackFlow {
        try {
            val ref = repo.updateForm(
                projectId,
                programType,
                groupId,
                formId,
                form
            )

            ref.update(form).addOnSuccessListener {
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
                close()
            }


        } catch (e: IOException) {
            trySend(Result.failure(Throwable("Error conectando con la base de datos.")))
            Log.d("UseCase", "UpdateGroupClassesUseCase $e")
        } catch (e: Exception) {
            trySend(Result.failure(e))
        }

    }

}