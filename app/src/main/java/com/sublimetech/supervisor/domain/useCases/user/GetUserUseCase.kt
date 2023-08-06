package com.sublimetech.supervisor.domain.useCases.user

import android.util.Log
import com.sublimetech.supervisor.data.model.user.UserDto
import com.sublimetech.supervisor.domain.repositories.users.UsersRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repo: UsersRepository
) {

    suspend operator fun invoke(userId: String): Result<UserDto> {
        var result: Result<UserDto>? = null
        Log.d("UseCase", "GetUserUseCase")

        try {
            repo.getUser(userId).addOnSuccessListener { documentSnapshot ->
                result = if (documentSnapshot.exists()) {
                    Result.success(documentSnapshot.toObject(UserDto::class.java) as UserDto)
                } else {
                    Result.failure(Throwable("Error obteniendo  usuario."))
                }
            }.addOnFailureListener { exception ->
                result = Result.failure(Throwable("Error obteniendo usuario."))
            }.await()
        } catch (e: IOException) {
            result = Result.failure(Throwable("Error conectando con la base de datos."))
        }
        return result ?: Result.success(UserDto())
    }

}

class GetUserSnapshotUseCase @Inject constructor(
    private val repo: UsersRepository
) {

    operator fun invoke(userId: String): Flow<Result<UserDto>> = callbackFlow {
        Log.d("UseCase", "GetUserSnapshotUseCase")

        try {
            val callback = repo.getUserReference(userId).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    trySend(Result.failure(Throwable("Error obteniendo usuario.")))
                    return@addSnapshotListener
                }
                val source = if (snapshot != null && snapshot.metadata.hasPendingWrites())
                    "Local"
                else
                    "Server"

                if ((snapshot != null) && snapshot.exists()) {
                    trySend(Result.success(snapshot.toObject(UserDto::class.java) as UserDto))
                    Log.d("UseCase", "$source data")

                } else {
                    trySend(
                        Result.failure(Throwable("Error obteniendo  usuario."))
                    )
                }

            }
            awaitClose {
                callback.remove()
                close()
            }


        } catch (e: IOException) {
            trySend(Result.failure(Throwable("Error conectando con la base de datos.")))
        }
    }

}