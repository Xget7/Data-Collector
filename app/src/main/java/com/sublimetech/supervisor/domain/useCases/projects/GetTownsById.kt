package com.sublimetech.supervisor.domain.useCases.projects

import android.util.Log
import com.sublimetech.supervisor.data.model.locations.TownDto
import com.sublimetech.supervisor.domain.repositories.project.ProjectRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.io.IOException
import javax.inject.Inject

class GetTownsById @Inject constructor(
    private val repo: ProjectRepository
) {

    operator fun invoke(userId: String) = callbackFlow<Result<List<TownDto>>> {
        Log.d("invoked", "GetTownsById")
        try {

            val callback = repo.getTownsById(userId)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        trySend(Result.failure(Throwable("Error obteniendo formatos.")))
                        return@addSnapshotListener
                    }
                    if (snapshot != null && !snapshot.isEmpty) {
                        val classes = snapshot.toObjects(TownDto::class.java)
                        trySend(Result.success(classes))
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
            Log.d("UseCase", "GetTownsById $e")
        } catch (e: Exception) {
            trySend(Result.failure(Throwable("Error inesperado.")))
            Log.d("UseCase", "GetTownsById $e")
        }
    }

}