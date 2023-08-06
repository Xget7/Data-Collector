package com.sublimetech.supervisor.presentation.ui.profesional.family.newForm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.sublimetech.supervisor.data.model.admin.ProjectDto
import com.sublimetech.supervisor.data.model.family.FamilyDto

data class FamilyNewFormUiState(
    val isLoading: Boolean = false,
    val family: MutableState<FamilyDto> = mutableStateOf(FamilyDto()),
    val project: ProjectDto = ProjectDto(),
    val isSuccessFamilySaved: Boolean = false,
    val isError: String? = null

)
