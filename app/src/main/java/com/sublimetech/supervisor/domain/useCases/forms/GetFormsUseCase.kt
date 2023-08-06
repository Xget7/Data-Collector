package com.sublimetech.supervisor.domain.useCases.forms

import android.util.Log
import com.sublimetech.supervisor.data.model.youtAndWoman.FormDto
import com.sublimetech.supervisor.domain.model.youthAndWoman.Form
import com.sublimetech.supervisor.domain.repositories.professional.FormRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class GetFormsUseCase @Inject constructor(
    private val repo: FormRepository
) {

    suspend operator fun invoke(
        projectId: String,
        programType: String,
        groupId: String,
    ): Result<List<FormDto>> {
        var result: Result<List<FormDto>>? = null
        try {
            repo.getForms(projectId, programType, groupId).addOnSuccessListener {
                val classes = it.toObjects(FormDto::class.java)
                result = Result.success(classes)
            }.addOnFailureListener { exception ->
                result = Result.failure(Throwable("Error obteniendo clases del grupo."))
                Log.d("UseCase", "UpdateGroupClassesUseCase $exception")
            }.await()
        } catch (e: IOException) {
            result = Result.failure(Throwable("Error conectando con la base de datos."))
            Log.d("UseCase", "UpdateGroupClassesUseCase $e")
        } catch (e: Exception) {
            result = Result.failure(e)
            Log.d("UseCase", "UpdateGroupClassesUseCase $e")
        }

        return result ?: Result.failure(Throwable("Error en la base de datos FORMS USE CASE"))
    }

}

class GetFormsSnapshotUseCase @Inject constructor(
    private val repo: FormRepository,
) {
    val TAG = "GetFormsSnapshotUseCase"

    operator fun invoke(
        projectId: String,
        programType: String,
        groupsIds: String,
    ): Flow<Result<List<Form>>> = callbackFlow {
        try {
            val forms = mutableListOf<Form>()
            val callback = repo.getFormsSnapshot(projectId, programType, groupsIds)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        trySend(Result.failure(Throwable("Error obteniendo formatos.")))
                        return@addSnapshotListener
                    }
                    if (snapshot != null && !snapshot.isEmpty) {
                        forms.clear()
                        val classes = snapshot.toObjects(FormDto::class.java)
                        classes.forEach {
                            forms.add(it.toForm(false))
                        }
                        trySend(Result.success(forms))
                    } else {
                        trySend(Result.success(emptyList()))
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
            Log.d("UseCase", "UpdateGroupClassesUseCase $e")
        }

    }


}