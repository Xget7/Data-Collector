package com.sublimetech.supervisor.presentation.ui.profesional.woman.updateForm

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sublimetech.supervisor.data.model.user.UserDto
import com.sublimetech.supervisor.domain.model.WomanForm.Deliverables
import com.sublimetech.supervisor.domain.model.WomanForm.DeliverablesData
import com.sublimetech.supervisor.domain.model.WomanForm.OfflineFormEntity
import com.sublimetech.supervisor.domain.model.youthAndWoman.Form
import com.sublimetech.supervisor.domain.repositories.images.OfflineFormsRepository
import com.sublimetech.supervisor.domain.useCases.forms.FormsUseCases
import com.sublimetech.supervisor.domain.useCases.groups.GroupsUseCases
import com.sublimetech.supervisor.domain.useCases.projects.ProjectUseCases
import com.sublimetech.supervisor.domain.useCases.storage.FirebaseStorageUseCases
import com.sublimetech.supervisor.domain.useCases.user.UserUseCases
import com.sublimetech.supervisor.presentation.utils.Utils
import com.sublimetech.supervisor.presentation.utils.Utils.WOMAN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WomanUpdateFormViewModel @Inject constructor(
    private val formsRepo: FormsUseCases,
    private val userUseCases: UserUseCases,
    private val groupsUseCases: GroupsUseCases,
    private val projectUseCases: ProjectUseCases,
    private val offlineRepo: OfflineFormsRepository,
    private val fbStorageUseCases: FirebaseStorageUseCases,

    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(WomanUpdateFormUiState())
    val uiState = _uiState.asStateFlow()

    val currentForm = mutableStateOf(Form())

    private val userId = Firebase.auth.currentUser?.uid
    private val formId = mutableStateOf("")
    private val projectId = mutableStateOf("")
    private val groupId = mutableStateOf("")
    val deliverablesData = mutableStateOf(DeliverablesData())
    val newDeliverablesData = mutableStateOf(DeliverablesData())

    var offlineCurrentForm: OfflineFormEntity? = null


    init {
        savedStateHandle.get<String>("projectId")?.let {
            projectId.value = it

        }
        savedStateHandle.get<String>("groupId")?.let {
            groupId.value = it
        }
        savedStateHandle.get<String>("formId")?.let {
            formId.value = it
        }

        loading(true)
        getCurrentForm(formId.value)
    }


    private fun getCurrentForm(id: String) {
        viewModelScope.launch {
            val result = formsRepo.getFormById(projectId.value, WOMAN, groupId.value, id)
            if (result.isSuccess) {
                currentForm.value = result.getOrThrow()
                parseFormToLocal()
                getProjectData(projectId.value)
            } else {
                _uiState.update {
                    _uiState.value.copy(
                        isLoading = false,
                        isError = result.exceptionOrNull()?.localizedMessage
                    )
                }
            }
        }
    }

    private fun getProjectData(id: String) {
        loading(true)
        val mResult = projectUseCases.getProjectUseCase.invoke(id)
        mResult.onEach { result ->
            if (result.isSuccess) {
                _uiState.update {
                    _uiState.value.copy(project = result.getOrThrow())
                }
                getGroupData(projectId.value, groupId.value)
                getUserData(userId ?: "")


            } else {
                _uiState.update {
                    _uiState.value.copy(
                        isError = result.exceptionOrNull()?.localizedMessage
                            ?: "Error obteniendo datos del proyecto.", isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)

    }


    private fun getSignatureNames(adminId: String, supervisorId: String, userDto: UserDto) {
        viewModelScope.launch {
            val result = userUseCases.getUserForSignatureUseCase.invoke(adminId, supervisorId)
            Log.d("signatureREsult", result.getOrNull().toString())
            if (result.isSuccess) {
                val finalResult = result.getOrThrow()
                userDto.let {
                    finalResult.put("PROFESSIONAL", "${it.fullName} - ${it.documentNumber}")
                }
                for (i in finalResult.values) {
                    deliverablesData.value.representatives.add(i)
                }
                _uiState.update {
                    _uiState.value.copy(
                        representatives = finalResult.values.toMutableStateList(),
                        isLoading = false
                    )
                }
            } else {
                delay(1000)
                _uiState.update {
                    _uiState.value.copy(
                        isError = result.exceptionOrNull()?.localizedMessage
                            ?: "Error obteniendo datos del administrador y supervisor.",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun getGroupData(projectId: String, groupId: String) {
        val mResult = groupsUseCases.getGroupUseCase.invoke(projectId, groupId, WOMAN)
        mResult.onEach { result ->
            if (result.isSuccess) {
                val groups = result.getOrThrow()
                _uiState.update {
                    _uiState.value.copy(
                        group = groups,
                    )
                }
                deliverablesData.value.attendees.clear()
                deliverablesData.value.attendees.addAll(
                    groups.students.toMutableList()
                )
                getUserData(userId ?: "")
            } else {
                _uiState.update {
                    _uiState.value.copy(
                        isError = result.exceptionOrNull()?.localizedMessage
                            ?: "Error obteniendo datos del grupo.", isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun uploadPhoto(uri: Uri): String {
        if (uri.toString().isEmpty()) return "No Photo"
//        if (offlineRepo.hasInternetConnectivity()) {
//            val result = withContext(viewModelScope.coroutineContext) {
//                fbStorageUseCases.uploadImageUseCase.invoke(
//                    uri, "projects/${projectId.value}/${formId.value}}"
//                )
//            }
//            return if (result.isSuccess) {
//                result.getOrThrow()
//            } else {
//                _uiState.update {
//                    _uiState.value.copy(isLoading = false)
//                }
//                uri.toString()
//            }
//        }
        return uri.toString()
    }


    private fun insertFormWithUris() {
        viewModelScope.launch {
            offlineCurrentForm = null
            offlineCurrentForm = OfflineFormEntity(
                formId = currentForm.value.id,
                groupId = groupId.value,
                formWithUris = currentForm.value,
                projectId = projectId.value,
                programType = WOMAN
            )
            if (currentForm.value.isLocal) {
                Log.d("updatePendingForm", " true")
                offlineCurrentForm?.let {
                    it.formWithUris.isLocal = true
                    offlineRepo.updatePendingForm(it)
                }
            } else {
                Log.d("updatePendingForm", "FALSE")
                offlineCurrentForm?.let {
                    it.formWithUris.isLocal = true
                    offlineRepo.insertPendingForm(
                        it
                    )
                }
            }
            _uiState.update {
                _uiState.value.copy(successFormUpload = true, isLoading = false)
            }
        }


    }


    fun updateForm() {
        if (checkForm()) {
            parseNewValuesToForm()
            insertFormWithUris()
            formsRepo.updateFormUseCase.invoke(
                projectId.value,
                WOMAN,
                groupId.value,
                formId.value,
                currentForm.value.toFormDto().updateFormToMap()
            ).launchIn(viewModelScope)
        }


    }

    private fun parseNewValuesToForm() {

        deliverablesData.value.developmentEvidence.value.let { evidence ->
            val photoTypes = mapOf(
                "freePhoto" to evidence.freePhoto.value,
                "groupPhoto" to evidence.groupPhoto.value,
                "selfie" to evidence.selfie.value,
                "teachingClass" to evidence.teachingClass.value
            )
            for ((photoType, photoValue) in photoTypes) {
                if (!photoValue.contains("firebasestorage.googleapis")) {
                    val uploadedPhoto = uploadPhoto(photoValue.toUri())
                    currentForm.value.developmentEvidenceList[photoType] = uploadedPhoto
                    when (photoType) {
                        "freePhoto" -> evidence.freePhoto.value = uploadedPhoto
                        "groupPhoto" -> evidence.groupPhoto.value = uploadedPhoto
                        "selfie" -> evidence.selfie.value = uploadedPhoto
                        "teachingClass" -> evidence.teachingClass.value = uploadedPhoto
                    }
                }
            }
        }
        deliverablesData.value.deliverablesList.forEach { (key, value) ->
            if (!value.image.contains("firebasestorage.googleapis")) {
                val uploadedImage = uploadPhoto(value.image.toUri())
                deliverablesData.value.deliverablesList[key]?.image = uploadedImage
            }
        }
        currentForm.value = currentForm.value.copy().apply {
            deliverablesList = this@WomanUpdateFormViewModel.deliverablesData.value.deliverablesList
        }
    }


    private fun getUserData(userId: String) {
        val result_ = userUseCases.getUserSnapshotUseCase.invoke(userId)
        result_.onEach { result ->
            if (result.isSuccess) {
                val user = result.getOrThrow()
                _uiState.update {
                    _uiState.value.copy(user = user)
                }
                getSignatureNames(
                    _uiState.value.project!!.adminId,
                    _uiState.value.project!!.supervisorId,
                    user
                )
                return@onEach
            } else {
                _uiState.update {
                    _uiState.value.copy(
                        isError = result.exceptionOrNull()?.localizedMessage
                            ?: "Error obteniendo datos del usuario.", isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun parseFormToLocal() {
        deliverablesData.value.deliverablesList = currentForm.value.deliverablesList
        deliverablesData.value.representativeSignaturesList =
            currentForm.value.representativeSignaturesList.values.toMutableList()
        currentForm.value.developmentEvidenceList.let { it ->
            deliverablesData.value.developmentEvidence.value.groupPhoto.value =
                it["groupPhoto"] as String
            deliverablesData.value.developmentEvidence.value.selfie.value =
                it["selfie"] as String
            deliverablesData.value.developmentEvidence.value.freePhoto.value =
                it["freePhoto"] as String
            deliverablesData.value.developmentEvidence.value.teachingClass.value =
                it["teachingClass"] as String
            currentForm.value.attendancesList.forEach { attendances ->
                deliverablesData.value.attendanceList[attendances.key] = attendances.value
            }
        }
        _uiState.update {
            _uiState.value.copy(deliverablesList = currentForm.value.deliverablesList.values.toMutableStateList())
        }
    }

    fun updateDeliverables(del: Deliverables) {
        val newList = _uiState.value.deliverablesList
        newList.remove(del)

        _uiState.update {
            _uiState.value.copy(deliverablesList = newList)
        }
    }

    private fun loading(isLoading: Boolean) {
        _uiState.update {
            _uiState.value.copy(isLoading = isLoading)
        }
    }

    private fun checkForm(): Boolean {
        if (Utils.DEBUG) return true
        var evidence = true
        var atendances = false
        deliverablesData.value.developmentEvidence.value.apply {
            if (freePhoto.value.isEmpty()) {
                evidence = false
            } else if (groupPhoto.value.isEmpty()) {
                evidence = false
            } else if (teachingClass.value.isEmpty()) {
                evidence = false
            } else if (freePhoto.value.isEmpty()) {
                evidence = false
            }
        }
        if (!deliverablesData.value.attendanceList.values.isEmpty()) {
            atendances = true
        }
        if (!evidence) {
            _uiState.update {
                _uiState.value.copy(
                    isError = "Por favor, sube las 4 fotos requeridas en el campo 'Evidencias de desarrollo'"
                )
            }
            return false
        }
        if (!atendances) {
            _uiState.update {
                _uiState.value.copy(
                    isError = "Debe existir por lo menos una firma en la lista de asistencia."
                )
            }
            return false
        }
        return true

    }

}