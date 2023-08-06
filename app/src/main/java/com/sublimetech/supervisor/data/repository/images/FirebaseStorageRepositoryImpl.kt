package com.sublimetech.supervisor.data.repository.images

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.sublimetech.supervisor.domain.repositories.images.FirebaseStorageRepository
import java.io.File
import java.util.UUID
import javax.inject.Inject

class FirebaseStorageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage
) : FirebaseStorageRepository {


    override fun uploadImage(uri: Uri, reference: String): UploadTask {
        val ref = storage.getReference(reference).child(UUID.randomUUID().toString())
        return ref.putFile(uri)
    }

    override fun downloadFile(reference: String, id: String, file: File): Task<ByteArray> {
        val ref = storage.getReference(reference).child(id)
        return ref.getBytes(Long.MAX_VALUE)
    }

}