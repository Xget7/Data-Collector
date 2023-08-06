package com.sublimetech.supervisor.presentation.ui.profesional.family.updateForm

import com.sublimetech.supervisor.data.model.admin.ProjectDto
import com.sublimetech.supervisor.data.model.family.FamilyDto

data class FamilyUpdateFormUiState(
    val isLoading: Boolean = false,
    val project: ProjectDto = ProjectDto(),
    val family: FamilyDto = FamilyDto(),
    val isSuccessFamilyUpdated: Boolean = false,
    val isError: String? = null
)
