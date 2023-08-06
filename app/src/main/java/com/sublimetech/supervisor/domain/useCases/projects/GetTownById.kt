package com.sublimetech.supervisor.domain.useCases.projects

import android.util.Log
import com.sublimetech.supervisor.data.model.locations.TownDto
import com.sublimetech.supervisor.domain.repositories.project.ProjectRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.io.IOException
import javax.inject.Inject

class GetTownById @Inject constructor(
    private val repo: ProjectRepository
) {
    operator fun invoke(userId: String) = callbackFlow {
        try {
            repo.getTownById(userId).addOnSuccessListener { i ->
                val town = i.toObject(TownDto::class.java)
                if (town != null) {
                    trySend(Result.success(town))
                } else {
                    trySend(Result.failure(Throwable("No se encontró el municipio.")))
                }
                close()
            }.addOnFailureListener { exception ->
                trySend(Result.failure(Throwable("Error obteniendo municipio.")))
                Log.d("UseCase", "GetTownById $exception")
            }
            awaitClose {
                close()
            }
        } catch (e: IOException) {
            trySend(Result.failure(Throwable("Error conectando con la base de datos.")))
            Log.d("UseCase", "GetTownById $e")
        } catch (e: Exception) {
            trySend(Result.failure(Throwable("Error inesperado.")))
            Log.d("UseCase", "GetTownById $e")
        }
    }

}