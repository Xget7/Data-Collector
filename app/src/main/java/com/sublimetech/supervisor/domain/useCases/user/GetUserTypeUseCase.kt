package com.sublimetech.supervisor.domain.useCases.user

import android.util.Log
import com.sublimetech.supervisor.domain.model.BasicUserTypes
import com.sublimetech.supervisor.domain.repositories.users.UsersRepository
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class GetUserTypeUseCase @Inject constructor(
    private val repo: UsersRepository
) {

    suspend operator fun invoke(
        userId: String,
        field: String = "userType"
    ): Result<BasicUserTypes> {
        var result: Result<BasicUserTypes>? = null
        Log.d("invoked", "GetUserTypeUseCase")

        try {
            repo.getUser(userId).addOnSuccessListener { documentSnapshot ->
                result = if (documentSnapshot.exists()) {
                    Result.success(
                        BasicUserTypes(
                            professionalType = documentSnapshot["professionalType"] as String,
                            userType = documentSnapshot[field] as String
                        )
                    )
                } else {
                    Result.failure(Throwable("Error obteniendo el tipo de usuario."))
                }
            }.addOnFailureListener { exception ->
                result = Result.failure(Throwable("Error obteniendo el tipo de usuario."))
            }.await()
        } catch (e: IOException) {
            result = Result.failure(Throwable("Error conectando con la base de datos."))
        }
        return result ?: Result.failure(Throwable("Error en la base de datos GET USER TYPE"))
    }

}


class GetUserForSignatureUseCase @Inject constructor(
    private val repo: UsersRepository
) {

    suspend operator fun invoke(
        adminId: String,
        supervisorId: String
    ): Result<HashMap<String, String>> {
        Log.d("invoked", "GetUserForSignatureUseCase")

        var result: Result<HashMap<String, String>>? = null
        val hashMap = hashMapOf<String, String>()
        try {
            repo.getUser(adminId).addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    hashMap["ADMIN"] =
                        "${documentSnapshot["fullName"]} - ${documentSnapshot["documentNumber"]}"
                }

            }.addOnFailureListener { exception ->
                result = Result.failure(Throwable("Error obteniendo firmas."))
            }.await()
            repo.getUser(supervisorId).addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    hashMap["SUPERVISOR"] =
                        "${documentSnapshot["fullName"]} - ${documentSnapshot["documentNumber"]}"
                    // Log.d("GetUserForSignatureUseCase", "SUPERVISOR Exist ${documentSnapshot["fullName"]} - ${documentSnapshot["documentNumber"]} ")
                }
            }.addOnFailureListener { exception ->
                result = Result.failure(Throwable("Error obteniendo firmas."))
            }.await()
            Log.d("GetUserForSignatureUseCase", hashMap.toString())
            result = Result.success(hashMap)
        } catch (e: IOException) {
            result = Result.failure(Throwable("Error conectando con la base de datos."))
        }
        return result ?: Result.failure(Throwable("Error en la base de datos"))
    }

}