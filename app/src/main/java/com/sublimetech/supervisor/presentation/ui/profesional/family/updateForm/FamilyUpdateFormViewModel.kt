package com.sublimetech.supervisor.presentation.ui.profesional.family.updateForm

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.sublimetech.supervisor.data.model.family.FamilyMemberDto
import com.sublimetech.supervisor.data.model.family.FamilyRelationship
import com.sublimetech.supervisor.data.model.family.FamilyStudentDto
import com.sublimetech.supervisor.data.model.family.ProgramFeedback
import com.sublimetech.supervisor.data.model.family.VisitDto
import com.sublimetech.supervisor.data.model.youtAndWoman.StudentDto
import com.sublimetech.supervisor.domain.model.VisitEntity
import com.sublimetech.supervisor.domain.repositories.images.OfflineFormsRepository
import com.sublimetech.supervisor.domain.repositories.professional.FamilyLocalRepository
import com.sublimetech.supervisor.domain.useCases.family.FamiliesUseCases
import com.sublimetech.supervisor.domain.useCases.projects.ProjectUseCases
import com.sublimetech.supervisor.domain.useCases.storage.FirebaseStorageUseCases
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
class FamilyUpdateFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val familyUseCase: FamiliesUseCases,
    val familyLocalRepository: FamilyLocalRepository,
    val projectUseCases: ProjectUseCases,
    private val fbStorageUseCases: FirebaseStorageUseCases,
    private val offlineRepo: OfflineFormsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(FamilyUpdateFormUiState())
    val uiState = _uiState.asStateFlow()


    val studentInfo = mutableStateOf(FamilyStudentDto())
    var relativesInfo: MutableList<MutableState<FamilyMemberDto>> = mutableListOf()
    val familyRelationship = mutableStateOf(FamilyRelationship())
    val visitMutable = mutableStateOf(VisitDto())
    val feedback = mutableStateOf(ProgramFeedback())
    val attendacePeople = mutableListOf<StudentDto>()

    private var localFamily: VisitEntity? = null

    var retry = 0
    val projectId = mutableStateOf("")
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val familyId = mutableStateOf("")

    init {
        savedStateHandle.get<String>("projectId")?.let {
            projectId.value = it
            getProject(it)
        }
        savedStateHandle.get<String>("familyId")?.let {
            familyId.value = it
            getFamily(it)
        }
    }

    private fun getFamily(id: String) {
        val mResult = familyUseCase.getFamilyUseCase.invoke(projectId.value, id)
        mResult.onEach { result ->
            if (result.isSuccess) {
                val family = result.getOrThrow()
                family.apply {
                    studentInfo.value = this.student!!
                    members.forEach {
                        Log.d("relativesToAddfromMembers", it.toString())
                        relativesInfo.add(mutableStateOf(it))
                    }
                    Log.d("relativesInfo After add", relativesInfo.toString())
                    familyRelationship.value = visit.relationship ?: FamilyRelationship()
                    visitMutable.value = visit
                    if (visit.feedBack != null) {
                        feedback.value = visit.feedBack!!
                    }
                }
                localFamily = VisitEntity(
                    id = family.id,
                    familyDto = family
                )
            } else {
                _uiState.update {
                    _uiState.value.copy(
                        isError = result.exceptionOrNull()?.localizedMessage
                            ?: "Error obteniendo datos de la familia.", isLoading = false
                    )
                }
                delay(2000)
                if (retry == 0) {
                    getFamily(id)
                    retry++
                }
            }
        }.launchIn(viewModelScope)
    }


    fun configureBeforeUpload() {
        _uiState.update {
            _uiState.value.copy(isLoading = true)
        }
        visitMutable.value.relationship = familyRelationship.value
        visitMutable.value.feedBack = feedback.value

        localFamily?.familyDto = localFamily!!.familyDto.copy(
            members = relativesInfo.map { it.value },
            student = studentInfo.value,
            visit = visitMutable.value,
        )
        Log.d("configured New family ", localFamily?.familyDto.toString())
        insertFamilyWithUris()
        Log.d("Inserted New family ", localFamily?.familyDto.toString())
        familyUseCase.updateFamilyUseCase.invoke(
            projectId = projectId.value,
            familyId = familyId.value,
            familyMap = localFamily?.familyDto!!.toMap()
        ).launchIn(viewModelScope)

        _uiState.update {
            _uiState.value.copy(isSuccessFamilyUpdated = true, isLoading = false)
        }
    }

    private fun insertFamilyWithUris() {
        viewModelScope.launch {
            familyLocalRepository.insertVisit(
                localFamily!!
            )
        }
    }


    fun insertAttendancePeople() {
        attendacePeople.clear()
        attendacePeople.add(
            StudentDto(
                name = studentInfo.value.name,
                documentId = studentInfo.value.docNumber
            ),
        )
        relativesInfo.forEach {
            if (it.value.name.isNotBlank() && it.value.documentNumber.isNotBlank()) {
                attendacePeople.add(
                    StudentDto(
                        name = it.value.name,
                        documentId = it.value.documentNumber
                    )
                )
            }
        }
    }


    private fun getProject(id: String) {
        val mResult = projectUseCases.getProjectUseCase.invoke(id)
        mResult.onEach { result ->
            if (result.isSuccess) {
                _uiState.update {
                    _uiState.value.copy(project = result.getOrThrow())
                }

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


}