package com.sublimetech.supervisor.domain.repositories.images

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.UploadTask
import java.io.File

interface FirebaseStorageRepository {

    fun uploadImage(uri: Uri, reference: String): UploadTask

    fun downloadFile(reference: String, id: String, file: File): Task<ByteArray>
}