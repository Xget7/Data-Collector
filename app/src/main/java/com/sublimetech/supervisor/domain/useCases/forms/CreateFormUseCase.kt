package com.sublimetech.supervisor.domain.useCases.forms

import android.util.Log
import com.sublimetech.supervisor.data.model.youtAndWoman.FormDto
import com.sublimetech.supervisor.domain.repositories.professional.FormRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.IOException
import javax.inject.Inject

class CreateFormUseCase @Inject constructor(
    private val repo: FormRepository,
) {

    val TAG = "CreateFormUseCase"
    operator fun invoke(
        projectId: String,
        projectType: String,
        groupId: String,
        form: FormDto
    ): Flow<Result<Boolean>> = callbackFlow {
        try {
            val docRef = repo.createForm(projectId, projectType, groupId, form)
            docRef.set(form).addOnSuccessListener {
                Log.d(TAG, "Se subio a firebase callback sucess")
                trySend(Result.success(true))
            }.addOnFailureListener {
                Log.d("addOnFailureListener??", it.toString())
                trySend(Result.failure(it))
            }
            val callback = docRef.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    trySend(Result.failure(Throwable("Error creando formulario.")))
                    return@addSnapshotListener
                }
                val source = if (snapshot != null && snapshot.metadata.hasPendingWrites())
                    "Local"
                else
                    "Server"
                if (snapshot != null && snapshot.exists()) {
                    Log.d(
                        "snapshot != null && snapshot.exists()",
                        "${snapshot.exists()} ${snapshot.data}"
                    )
                    trySend(Result.success(true))
                } else {
                    trySend(Result.failure(Throwable("Error creando formulario.")))
                    Log.d(TAG, "$source data: null")
                }
            }
            awaitClose {
                callback.remove()
                this.cancel()
                this.close()
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