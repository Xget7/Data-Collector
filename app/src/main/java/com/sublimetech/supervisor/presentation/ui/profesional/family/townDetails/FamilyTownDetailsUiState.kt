package com.sublimetech.supervisor.presentation.ui.profesional.family.townDetails

import com.sublimetech.supervisor.data.model.admin.ProjectDto
import com.sublimetech.supervisor.data.model.family.FamilyDto
import com.sublimetech.supervisor.data.model.locations.TownDto

data class FamilyTownDetailsUiState(
    val isLoading: Boolean = false,
    val isError: String? = null,
    val town: TownDto? = null,
    val project: ProjectDto? = null,
    val families: List<FamilyDto> = emptyList(),
)
