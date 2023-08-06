package com.sublimetech.supervisor.domain.useCases.storage.files

import com.sublimetech.supervisor.domain.repositories.images.FirebaseStorageRepository
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.IOException
import javax.inject.Inject

class DownloadFileUseCase @Inject constructor(
    private val repo: FirebaseStorageRepository,


    ) {

    suspend operator fun invoke(reference: String, id: String, file: File): Result<ByteArray> {
        var result: Result<ByteArray>? = null


        try {
            repo.downloadFile(reference, id, file)
                .addOnSuccessListener {
                    result = Result.success(it)
                }.addOnFailureListener { exception ->
                    result = Result.failure(Throwable("Error descargando archivo."))
                }.await()

        } catch (e: IOException) {
            result = Result.failure(Throwable("Error conectando con la base de datos."))
        }
        return result ?: Result.failure(Throwable("Error en la base de datos DOWNLOAD FILE"))
    }

}