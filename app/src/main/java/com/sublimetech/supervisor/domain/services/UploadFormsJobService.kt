package com.sublimetech.supervisor.domain.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.sublimetech.supervisor.domain.model.WomanForm.RepresentativeSignature
import com.sublimetech.supervisor.domain.repositories.images.OfflineFormsRepository
import com.sublimetech.supervisor.domain.useCases.forms.FormsUseCases
import com.sublimetech.supervisor.domain.useCases.storage.FirebaseStorageUseCases
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class UploadFormsJobService : JobService() {

    //TODO("FILTER BY WOMAN AND YOUTH OR BY USER ID)
    private var job: Job? = null

    private val mainDispatcher = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var formsRepo: OfflineFormsRepository

    @Inject
    lateinit var storageUseCase: FirebaseStorageUseCases

    @Inject
    lateinit var formUseCases: FormsUseCases

    private var atLeastImageUploaded = false

    override fun onStartJob(params: JobParameters?): Boolean {
        atLeastImageUploaded = false
        job = mainDispatcher.launch {
            // Aquí puedes implementar la lógica para subir las imágenes pendientes
            // cuando hay conexión a Internet
            formsRepo.getPendingForms().collect { forms ->
                if (forms.isNotEmpty()) {
                    forms.forEach { form ->
                        Log.d("FormBeforeImages", form.formWithUris.toString())
                        form.formWithUris = form.formWithUris.apply {
                            deliverablesList.forEach {
                                deliverablesList[it.key]?.image =
                                    if (!it.value.image.contains("firebasestorage.googleapis")) {
                                        uploadImage(it.value.image.toUri(), form.groupId, id)
                                    } else {
                                        it.value.image
                                    }
                            }
                            representativeSignaturesList.forEach {
                                representativeSignaturesList[it.key] =
                                    RepresentativeSignature(
                                        signatureUrl = if (!it.value.signatureUrl.contains("firebasestorage.googleapis")) {
                                            uploadImage(
                                                it.value.signatureUrl.toUri(),
                                                form.groupId,
                                                id
                                            )
                                        } else {
                                            it.value.signatureUrl
                                        },
                                        signatureNameAndId = it.value.signatureNameAndId,
                                        signatureType = it.value.signatureType
                                    )
                            }
                            developmentEvidenceList.forEach {
                                if (!it.value.contains("firebasestorage.googleapis")) {
                                    val uploadedImage = uploadImage(
                                        it.value.toUri(),
                                        form.groupId,
                                        id
                                    )
                                    when (it.key) {
                                        "freePhoto" -> developmentEvidenceList[it.key] =
                                            uploadedImage

                                        "groupPhoto" -> developmentEvidenceList[it.key] =
                                            uploadedImage

                                        "selfie" -> developmentEvidenceList[it.key] = uploadedImage
                                        "teachingClass" -> developmentEvidenceList[it.key] =
                                            uploadedImage
                                    }
                                } else {
                                    when (it.key) {
                                        "freePhoto" -> developmentEvidenceList[it.key] =
                                            it.value

                                        "groupPhoto" -> developmentEvidenceList[it.key] =
                                            it.value

                                        "selfie" -> developmentEvidenceList[it.key] = it.value
                                        "teachingClass" -> developmentEvidenceList[it.key] =
                                            it.value
                                    }
                                }
                            }
                            attendancesList.forEach {
                                attendancesList[it.key] =
                                    if (!it.value.contains("firebasestorage.googleapis")) {
                                        uploadImage(it.value.toUri(), form.groupId, id)
                                    } else {
                                        it.value
                                    }
                            }
                        }

                        Log.d("FormWithImages", form.formWithUris.toString())

                        val isCreated = formUseCases.checkFormExist.invoke(
                            form.projectId,
                            form.programType,
                            form.groupId,
                            form.formId
                        )

                        if (!isCreated) {
                            val result = formUseCases.createFormUseCase.invoke(
                                form.projectId,
                                form.programType,
                                form.groupId,
                                form.formWithUris.toFormDto()
                            )
                            Log.d("create result", result.toString())
                            result.onEach { Rresult ->
                                if (Rresult.isSuccess) {
                                    Log.d(
                                        "OfflineUpdatesReceiver Created Socess",
                                        Rresult.getOrThrow().toString()
                                    )
                                    formsRepo.deleteOfflineForm(form)
                                } else {
                                    formsRepo.updatePendingForm(form)
                                }
                            }.launchIn(mainDispatcher)
                        } else {
                            if (atLeastImageUploaded) {
                                val result = formUseCases.updateFormUseCase.invoke(
                                    form.projectId,
                                    form.programType,
                                    form.groupId,
                                    form.formId,
                                    form.formWithUris.toFormDto().updateFormToMap()
                                )
                                result.onEach { Rresult ->
                                    if (Rresult.isSuccess) {
                                        Log.d("updateSucess?", Rresult.getOrThrow().toString())
                                        formsRepo.deleteOfflineForm(form)
                                    } else {
                                        Log.d("updateFail?", Rresult.getOrThrow().toString())
                                        formsRepo.updatePendingForm(form)
                                    }
                                }.launchIn(mainDispatcher)
                            } else {
                                job?.cancel()
                            }

                        }

                    }
                }
            }
        }
        return true
    }


    override fun onStopJob(params: JobParameters?): Boolean {
        mainDispatcher.cancel()
        job?.cancel()
        return true
    }

    private suspend fun uploadImage(uri: Uri, groupId: String, formId: String): String {
        atLeastImageUploaded = true
        if (uri.toString() == "No Photo") return "null"
        val result = storageUseCase.uploadImageUseCase.invoke(uri, "$groupId/$formId")
        if (result.isSuccess) {
            Log.d("OfflineUpdatesReceiver Image", result.getOrThrow())
            return result.getOrThrow()
        }
        if (result.isFailure) {
            return uri.toString()
        }
        return uri.toString()
    }

}