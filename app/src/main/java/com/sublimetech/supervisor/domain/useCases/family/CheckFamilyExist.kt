package com.sublimetech.supervisor.domain.useCases.family

import android.util.Log
import com.sublimetech.supervisor.domain.repositories.professional.FamilyRepository
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class CheckFamilyExist @Inject constructor(
    private val repo: FamilyRepository
) {

    suspend operator fun invoke(
        projectId: String,
        familyId: String,
    ): Boolean {
        var result = false
        try {
            repo.getFamily(projectId, familyId).addOnSuccessListener {
                result = it.exists()
            }.addOnFailureListener { exception ->
                result = false
                Log.d("UseCase", "UpdateGroupClassesUseCase $exception")
            }.await()
        } catch (e: IOException) {
            result = false
            Log.d("UseCase", "UpdateGroupClassesUseCase $e")
        } catch (e: Exception) {
            result = false
            Log.d("UseCase", "UpdateGroupClassesUseCase $e")
        }
        return result
    }

}