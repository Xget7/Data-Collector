package com.sublimetech.supervisor.domain.useCases.family

import com.sublimetech.supervisor.data.model.family.VisitDto
import com.sublimetech.supervisor.domain.repositories.professional.FamilyRepository
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class CreateVisitUseCase @Inject constructor(
    private val repo: FamilyRepository
) {

    suspend operator fun invoke(projectId: String, family: VisitDto): Result<Boolean> {
        var result: Result<Boolean>? = null
        try {
            repo.createVisit(projectId, family).addOnSuccessListener {
                Result.success(true)
            }.addOnFailureListener { exception ->
                result = Result.failure(Throwable("Error al crear visita."))
            }.await()

        } catch (e: IOException) {
            result = Result.failure(Throwable("Error conectando con la base de datos."))
        }
        return result ?: Result.failure(Throwable("Error en la base de datos CREATE VISIT"))
    }

}