package com.sublimetech.supervisor.domain.useCases.user

import com.sublimetech.supervisor.data.model.user.UserDto
import com.sublimetech.supervisor.domain.repositories.users.UsersRepository
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repo: UsersRepository
) {

    suspend operator fun invoke(user: UserDto): Result<Boolean> {
        var result: Result<Boolean>? = null
        try {
            repo.createUser(user).addOnSuccessListener { i ->
                Result.success(true)
            }.addOnFailureListener { exception ->
                result = Result.failure(Throwable("Error creando usuario."))
            }.await()
        } catch (e: IOException) {
            result = Result.failure(Throwable("Error conectando con la base de datos."))
        }
        return result ?: Result.failure(Throwable("Error en la base de datos"))
    }

}