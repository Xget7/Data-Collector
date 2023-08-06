package com.sublimetech.supervisor.domain.useCases.storage.images

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.StorageException
import com.sublimetech.supervisor.domain.repositories.images.FirebaseStorageRepository
import com.sublimetech.supervisor.domain.repositories.images.OfflineFormsRepository
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UploadImageUseCase @Inject constructor(
    private val repo: FirebaseStorageRepository,
    private val networkConnection: OfflineFormsRepository
) {
    suspend operator fun invoke(uri: Uri?, reference: String): Result<String> =
        suspendCoroutine { cont ->
            if (uri.toString()
                    .isEmpty()
            ) {
                cont.resume(Result.success("No Photo"))
                return@suspendCoroutine
            }

            if (uri == null) {
                cont.resume(Result.success("No Photo"))
                return@suspendCoroutine
            }

            try {


                val task = repo.uploadImage(uri, reference)
                task.addOnSuccessListener { uploadTask ->
                    uploadTask.storage.downloadUrl.addOnSuccessListener { uri ->
                        Log.d("DownloadUrlSUcess", "yes")
                        cont.resume(Result.success(uri.toString()))
                        Log.d("downloadUrl", uri.toString())
                    }.addOnFailureListener {
                        Log.d("ImageExcp", "NOP")
                        cont.resume(Result.failure(Throwable("Error al subir imagen.")))
                    }
                }.addOnFailureListener { exception ->
                    cont.resume(Result.failure(Throwable("Error al subir imagen.")))
                    Log.d("ImageExcp", exception.message.toString())
                }


                if (!networkConnection.hasInternetConnectivity()) {
                    cont.resume(Result.failure(Throwable("Error al subir imagen.")))
                }

            } catch (e: IOException) {
                Log.d("ImageExcp", e.message.toString())
                cont.resume(Result.success(uri.toString()))

            } catch (e: StorageException) {
                Log.d("ImageExcp", e.message.toString())
                cont.resume(Result.success(uri.toString()))

            } catch (e: Exception) {
                cont.resume(Result.success(uri.toString()))
                Log.d("ImageExcp", e.message.toString())
            }
        }

}