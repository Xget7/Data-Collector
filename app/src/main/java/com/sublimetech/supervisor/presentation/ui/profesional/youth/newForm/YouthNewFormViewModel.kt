package com.sublimetech.supervisor.presentation.ui.profesional.youth.newForm

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
import com.sublimetech.supervisor.domain.model.WomanForm.RepresentativeSignature
import com.sublimetech.supervisor.domain.model.youthAndWoman.Form
import com.sublimetech.supervisor.domain.repositories.images.OfflineFormsRepository
import com.sublimetech.supervisor.domain.useCases.forms.FormsUseCases
import com.sublimetech.supervisor.domain.useCases.groups.GroupsUseCases
import com.sublimetech.supervisor.domain.useCases.projects.ProjectUseCases
import com.sublimetech.supervisor.domain.useCases.storage.FirebaseStorageUseCases
import com.sublimetech.supervisor.domain.useCases.user.UserUseCases
import com.sublimetech.supervisor.presentation.ui.profesional.woman.newForm.components.getCurrentDateTime
import com.sublimetech.supervisor.presentation.ui.profesional.woman.newForm.components.toString
import com.sublimetech.supervisor.presentation.utils.LocationUtils
import com.sublimetech.supervisor.presentation.utils.Utils
import com.sublimetech.supervisor.presentation.utils.Utils.YOUTH
import com.sublimetech.supervisor.presentation.utils.Utils.sessionNumberToArea
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class YouthNewFormViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val projectUseCases: ProjectUseCases,
    private val groupsUseCases: GroupsUseCases,
    private val formsUseCases: FormsUseCases,
    private val locationHelper: LocationUtils,
    private val offlineRepo: OfflineFormsRepository,
    private val fbStorageUseCases: FirebaseStorageUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(YouthNewFormUiState())

    val uiState = _uiState.asStateFlow()
    val deliverablesData = mutableStateOf(DeliverablesData())

    private var offlineCurrentForm: OfflineFormEntity? = null

    val currentForm = mutableStateOf(Form())


    val projectId = mutableStateOf("")
    val groupId = mutableStateOf("")
    private val userId = Firebase.auth.currentUser?.uid
    private val sessionNumber = mutableStateOf(1)
    private val formId = mutableStateOf("")

    init {
        savedStateHandle.get<String>("projectId")?.let {
            projectId.value = it

        }
        savedStateHandle.get<String>("groupId")?.let {
            groupId.value = it
        }
        savedStateHandle.get<String>("formNumber")?.let {
            sessionNumber.value = it.toInt()
        }

        if (projectId.value.isNotEmpty()) {
            loading(true)
            getProjectData(projectId.value)
            Log.d(
                "savedStateHandles",
                projectId.value + "/" + groupId.value + "/" + sessionNumber.value + 1
            )
        }
    }


    private fun getUserData(userId: String) {
        val result_ = userUseCases.getUserSnapshotUseCase.invoke(userId)

        result_.onEach { result ->
            if (result.isSuccess) {
                _uiState.update {
                    _uiState.value.copy(userData = result.getOrThrow())
                }
                Log.d(
                    "afterGetSignatures",
                    "${_uiState.value.project!!.adminId}, ${_uiState.value.project!!.supervisorId}"
                )
                getSignatureNames(
                    _uiState.value.project!!.adminId,
                    _uiState.value.project!!.supervisorId,
                    result.getOrThrow()
                )
                createFormData()
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

    private fun getProjectData(id: String) {
        loading(true)
        val mResult = projectUseCases.getProjectUseCase.invoke(id)
        mResult.onEach { result ->
            if (result.isSuccess) {
                _uiState.update {
                    _uiState.value.copy(project = result.getOrThrow())
                }
                getGroupData(projectId.value, groupId.value)

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
                    _uiState.value.copy(representatives = finalResult.values.toMutableStateList())
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
        val mResult = groupsUseCases.getGroupUseCase.invoke(projectId, groupId, YOUTH)

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

    private fun createFormData() {
        val date = getCurrentDateTime()
        currentForm.value = currentForm.value.apply {
            id = UUID.randomUUID().toString()
            uiState.value.userData?.let { usr ->
                professionalId = userId!!
                professionalName = usr.fullName!!
                professionalDocumentId = usr.documentNumber!!
            }
            startDate = date.toString("yyyy/MM/dd")
            startTime = date.toString("HH:mm")
            sessionNumber = this@YouthNewFormViewModel.sessionNumber.value + 1
            area = sessionNumberToArea(YOUTH, this@YouthNewFormViewModel.sessionNumber.value + 1)
            groupName = _uiState.value.group!!.name
            _uiState.update {
                _uiState.value.copy(deliverablesList = currentForm.value.deliverablesList.values.toMutableStateList())
            }
        }
        loading(false)
    }


    private fun loading(isLoading: Boolean) {
        _uiState.update {
            _uiState.value.copy(isLoading = isLoading)
        }
    }


    fun getLocation(upload: Boolean) {
        if (upload) {
            loading(true)
        }
        viewModelScope.launch {
            try {
                val result = locationHelper.getLastKnownLocation()?.getOrThrow()
                Log.d("locationResult", result.toString())
                result?.let { location ->
                    currentForm.value = currentForm.value.copy(
                        longitude = location.longitude,
                        latitude = location.latitude
                    )
                    if (upload) {
                        if (checkForm()) {
                            Log.d("CheckForm ", "TURE")
                            uploadForm()
                        } else Log.d("CheckForm ", "FALSE")

                    } else return@launch


                }
            } catch (e: Exception) {
                _uiState.update {
                    _uiState.value.copy(isError = "Error obteniendo localizacion.")
                }
            }

        }
    }


    private suspend fun uploadForm() {
        configureForm()
        insertFormWithUris()

        formsUseCases.createFormUseCase.invoke(
            projectId.value,
            YOUTH,
            groupId.value,
            currentForm.value.toFormDto()
        ).launchIn(viewModelScope)
    }


    private fun configureForm() {
        deliverablesData.value.deliverablesList.forEach {
            deliverablesData.value.deliverablesList[it.key]?.image =
                uploadPhoto(it.value.image.toUri())
        }

        deliverablesData.value.representativeSignaturesList.forEach {
            deliverablesData.value.representativeSignaturesList.add(
                RepresentativeSignature(
                    signatureUrl = uploadPhoto(it.signatureUrl.toUri()),
                    signatureNameAndId = it.signatureNameAndId,
                    signatureType = it.signatureType
                )
            )
        }
        deliverablesData.value.developmentEvidence.value.let {
            deliverablesData.value.developmentEvidence.value.freePhoto.value =
                uploadPhoto(it.freePhoto.value.toUri())
            deliverablesData.value.developmentEvidence.value.selfie.value =
                uploadPhoto(it.selfie.value.toUri())
            deliverablesData.value.developmentEvidence.value.teachingClass.value =
                uploadPhoto(it.teachingClass.value.toUri())
            deliverablesData.value.developmentEvidence.value.groupPhoto.value =
                uploadPhoto(it.groupPhoto.value.toUri())
        }
        deliverablesData.value.attendanceList.forEach {
            deliverablesData.value.attendanceList[it.key] = uploadPhoto(it.value.toUri())
        }
        currentForm.value = currentForm.value.copy().apply {
            attendancesList = this@YouthNewFormViewModel.deliverablesData.value.attendanceList
            this@YouthNewFormViewModel.deliverablesData.value.representativeSignaturesList.forEach {
                representativeSignaturesList[it.signatureType] = it
            }
            developmentEvidenceList =
                this@YouthNewFormViewModel.deliverablesData.value.developmentEvidence.value.toMap()
            this.deliverablesList =
                this@YouthNewFormViewModel.deliverablesData.value.deliverablesList
        }

        currentForm.value
    }

    private fun uploadPhoto(uri: Uri): String {
        if (uri.toString().isEmpty()) return "No Photo"
        return uri.toString()
    }

    private suspend fun insertFormWithUris() {
        Log.d("InsertLocalFOrm", currentForm.value.toString())
        offlineCurrentForm = null
        offlineCurrentForm = OfflineFormEntity(
            formId = currentForm.value.id,
            groupId = groupId.value,
            formWithUris = currentForm.value.copy(isLocal = true),
            projectId = projectId.value,
            programType = YOUTH
        )
        offlineRepo.insertPendingForm(
            offlineCurrentForm!!
        )
        _uiState.update {
            _uiState.value.copy(isLoading = false, successFormUpload = true)
        }

    }

    fun updateDeliverables(del: Deliverables) {
        val newList = _uiState.value.deliverablesList
        newList.remove(del)

        _uiState.update {
            _uiState.value.copy(deliverablesList = newList)
        }
    }

    fun clearError() {
        _uiState.update {
            _uiState.value.copy(isLoading = false, isError = null)
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
                    isError = "Por favor, sube las 4 fotos requeridas en el campo 'Evidencias de desarrollo'",
                    isLoading = false
                )
            }
            return false
        }
        if (!atendances) {
            _uiState.update {
                _uiState.value.copy(
                    isError = "Debe existir por lo menos una firma en la lista de asistencia.",
                    isLoading = false
                )
            }
            return false
        }
        return true

    }
}