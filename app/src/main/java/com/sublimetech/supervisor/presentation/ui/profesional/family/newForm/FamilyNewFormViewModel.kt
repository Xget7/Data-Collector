package com.sublimetech.supervisor.presentation.ui.profesional.family.newForm

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.sublimetech.supervisor.data.model.family.FamilyDto
import com.sublimetech.supervisor.data.model.family.FamilyMemberDto
import com.sublimetech.supervisor.data.model.family.FamilyRelationship
import com.sublimetech.supervisor.data.model.family.FamilyStudentDto
import com.sublimetech.supervisor.data.model.family.VisitDto
import com.sublimetech.supervisor.data.model.youtAndWoman.StudentDto
import com.sublimetech.supervisor.domain.model.VisitEntity
import com.sublimetech.supervisor.domain.repositories.professional.FamilyLocalRepository
import com.sublimetech.supervisor.domain.useCases.family.FamiliesUseCases
import com.sublimetech.supervisor.domain.useCases.projects.ProjectUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class FamilyNewFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val familyUseCase: FamiliesUseCases,
    val familyLocalRepository: FamilyLocalRepository,
    val projectUseCases: ProjectUseCases,
) : ViewModel() {

    private val _uiState = MutableStateFlow(FamilyNewFormUiState())
    val uiState = _uiState.asStateFlow()

    private val familyDto = mutableStateOf(FamilyDto())

    val studentInfo = mutableStateOf(FamilyStudentDto())
    val relativesInfo = mutableListOf(mutableStateOf(FamilyMemberDto()))
    val familyRelationship = mutableStateOf(FamilyRelationship())
    val visitMutable = mutableStateOf(VisitDto())
    val attendacePeople = mutableListOf<StudentDto>()

    private var localFamily: VisitEntity? = null


    val projectId = mutableStateOf("")
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val familyId = mutableStateOf("")

    init {
        savedStateHandle.get<String>("projectId")?.let {
            projectId.value = it
            getProject(it)
        }
        familyId.value = UUID.randomUUID().toString()
//        TODO( "familyRelationship NO SE UPLOAD ")
    }


    private fun getProject(id: String) {
        val mResult = projectUseCases.getProjectUseCase.invoke(id)
        mResult.onEach { result ->
            if (result.isSuccess) {
                _uiState.update {
                    _uiState.value.copy(project = result.getOrThrow())
                }
                Log.d("project", result.getOrThrow().toString())
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

    fun createFamilyAndUpload() {
        viewModelScope.launch {
            setUpFamily()
            insertFamilyWithUris()
            familyUseCase.createFamilyUseCase.invoke(familyDto.value).launchIn(viewModelScope)
        }
    }

    private fun setUpFamily() {
        _uiState.update {
            _uiState.value.copy(isLoading = true)
        }
        visitMutable.value.relationship = familyRelationship.value
        familyDto.value = familyDto.value.copy().apply {
            id = familyId.value
            professionalId = userId
            name = relativesInfo.first().value.name
            projectId = this@FamilyNewFormViewModel.projectId.value
            this.visit = visitMutable.value
            members = relativesInfo.map { it.value }
            student = studentInfo.value
            createdTime = System.currentTimeMillis()
        }

        familyDto.value = familyDto.value.copy().apply {
            visit.developmentPhotos.forEach {
                visit.developmentPhotos[it.key] = isUploadedImage(it.value)
            }
            visit.assistance.forEach {
                visit.assistance[it.key] = isUploadedImage(it.value)
            }
            visit.professionalSignature1?.let {
                visit.professionalSignature1 = isUploadedImage(it)
            }
            visit.guardianSignature.let {
                visit.guardianSignature = isUploadedImage(it)
            }
        }



        Log.d("familySetUp", familyDto.value.toString())


    }

    private fun isUploadedImage(image: String): String {
        return image
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

    private suspend fun insertFamilyWithUris() {
        localFamily = VisitEntity(
            id = familyId.value,
            familyDto = familyDto.value
        )
        familyLocalRepository.insertVisit(
            localFamily!!
        )
        _uiState.update {
            _uiState.value.copy(isSuccessFamilySaved = true, isLoading = false)
        }


    }

}