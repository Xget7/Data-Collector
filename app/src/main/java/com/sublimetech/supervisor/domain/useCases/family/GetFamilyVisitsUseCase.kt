package com.sublimetech.supervisor.domain.useCases.family

import com.sublimetech.supervisor.data.model.family.FamilyDto
import com.sublimetech.supervisor.data.model.family.VisitDto
import com.sublimetech.supervisor.domain.repositories.professional.FamilyRepository
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class GetFamilyVisitsUseCase @Inject constructor(
    private val repo: FamilyRepository
) {

    suspend operator fun invoke(projectId: String, familyId: String): Result<List<VisitDto>> {
        var result: Result<List<VisitDto>>? = null
        try {
            repo.getVisit(projectId, familyId).addOnSuccessListener { i ->
                Result.success(i.toObjects(FamilyDto::class.java))
            }.addOnFailureListener { exception ->
                result = Result.failure(Throwable("Error obteniendo visistas de la familia."))
            }.await()

        } catch (e: IOException) {
            result = Result.failure(Throwable("Error conectando con la base de datos."))
        }
        return result
            ?: Result.failure(Throwable("Error en la base de datos GET FAMILY VISIT USE CASE"))
    }

}