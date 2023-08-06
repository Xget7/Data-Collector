package com.sublimetech.supervisor.domain.providers

import android.content.Context
import android.os.Environment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class FilesProvider @Inject constructor(
    val context: Context
) {
    val db = Firebase.firestore.collection("app").document("app")

    fun getPdfUrl(): String {
        var result = ""
        db.get().addOnSuccessListener {
            result = it["pdfHelperUrl"] as String
        }.addOnFailureListener {
            result = "failure"
        }
        return result
    }


    fun createOrGetFile(fileName: String): File = runBlocking {
        val downloadsDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        val tempFile: File = withContext(Dispatchers.IO) {
            if (!downloadsDirectory.exists()) {
                downloadsDirectory.mkdirs()
            }
            val file = File(
                downloadsDirectory,
                fileName
            ) // Agrega la fecha y hora actual al nombre del archivo
            if (!file.exists()) {
                file.createNewFile()
                return@withContext file
            }
            file
        }

        return@runBlocking tempFile
    }


}