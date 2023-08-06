package com.sublimetech.supervisor.domain.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import androidx.core.net.toUri
import com.sublimetech.supervisor.domain.repositories.professional.FamilyLocalRepository
import com.sublimetech.supervisor.domain.useCases.family.FamiliesUseCases
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
class UploadVisitJobService : JobService() {
    private var job: Job? = null


    private val mainDispatcher = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var storageUseCase: FirebaseStorageUseCases

    @Inject
    lateinit var familyUseCases: FamiliesUseCases

    @Inject
    lateinit var visitLocalRepo: FamilyLocalRepository


    override fun onStartJob(params: JobParameters?): Boolean {
        job = mainDispatcher.launch {
            // Aquí puedes implementar la lógica para subir las imágenes pendientes
            // cuando hay conexión a Internet
            visitLocalRepo.getVisits().collect { localVisit ->
                localVisit.forEach {
                    Log.d("visits", it.toString())
                }
                if (localVisit.isNotEmpty()) {
                    localVisit.forEach { visitEntity ->

                        visitEntity.familyDto = visitEntity.familyDto.apply {
                            visit.developmentPhotos.forEach {

                                visit.developmentPhotos[it.key] = uploadImage(it.value, id)
                            }
                            visit.funDevelopmentPhotos.forEach {

                                visit.funDevelopmentPhotos[it.key] =
                                    it.value.copy(uri = uploadImage(it.value.uri, id))
                            }
                            visit.thematicDevelopmentPhotos.forEach {

                                visit.thematicDevelopmentPhotos[it.key] =
                                    it.value.copy(uri = uploadImage(it.value.uri, id))
                            }
                            visit.assistance.forEach {
                                visit.assistance[it.key] = uploadImage(it.value, id)
                            }
                            visit.assistance2.forEach {
                                visit.assistance2[it.key] = uploadImage(it.value, id)
                            }
                            visit.professionalSignature1?.let {
                                visit.professionalSignature1 = uploadImage(it, id)
                            }
                            visit.professionalSignature2?.let {
                                visit.professionalSignature2 = uploadImage(it, id)
                            }
                            visit.professionalSignature3?.let {
                                visit.professionalSignature3 = uploadImage(it, id)
                            }
                            visit.guardianSignature.let {
                                visit.guardianSignature = uploadImage(it, id)
                            }
                        }
                        Log.d("Iscreated familyId", visitEntity.familyDto.id)

                        val isCreated = familyUseCases.checkFamilyExist.invoke(
                            visitEntity.familyDto.projectId,
                            visitEntity.familyDto.id
                        )


                        if (!isCreated) {
                            familyUseCases.createFamilyUseCase.invoke(visitEntity.familyDto)
                                .onEach { result ->
                                    if (result.isSuccess) {
                                        Log.d(
                                            "UploadedImageFamilyDto Created Socess",
                                            result.getOrThrow().toString()
                                        )
                                        visitLocalRepo.deleteVisit(visitEntity)
                                        job?.cancel()
                                    } else {
                                        visitLocalRepo.updateVisit(visitEntity)
                                        job?.cancel()

                                    }
                                }.launchIn(mainDispatcher)
                        } else {
                            familyUseCases.updateFamilyUseCase.invoke(
                                visitEntity.familyDto.projectId,
                                visitEntity.familyDto.id,
                                visitEntity.familyDto.toMap()
                            ).onEach { result ->
                                if (result.isSuccess) {
                                    Log.d(
                                        "UploadedImageFamilyDto Created Socess",
                                        result.getOrThrow().toString()
                                    )
                                    visitLocalRepo.deleteVisit(visitEntity)
                                    job?.cancel()
                                } else {
                                    visitLocalRepo.updateVisit(visitEntity)
                                    job?.cancel()
                                }
                            }.launchIn(mainDispatcher)

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


    private suspend fun uploadImage(uri: String, familyId: String): String {
        if (uri.contains("firebasestorage.googleapis")) return uri
        if (uri == "No Photo") return "null"
        if (uri.isEmpty()) return "null"
        if (uri == "null") return "null"
        Log.d("Enter uri", uri)

        val result = storageUseCase.uploadImageUseCase.invoke(uri.toUri(), familyId)
        if (result.isSuccess) {
            Log.d("OfflineUpdatesReceiver Image", result.getOrThrow())
            Log.d("Success uri", uri)
            return result.getOrThrow()
        }
        if (result.isFailure) {
            Log.d("Failure uri", result.exceptionOrNull().toString())
            return uri
        }
        return uri
    }

}