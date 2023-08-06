package com.sublimetech.supervisor.domain.useCases.storage

import com.sublimetech.supervisor.domain.useCases.storage.files.DownloadFileUseCase
import com.sublimetech.supervisor.domain.useCases.storage.images.UploadImageUseCase

data class FirebaseStorageUseCases(
    val downloadFileUseCase: DownloadFileUseCase,
    val uploadImageUseCase: UploadImageUseCase
)
