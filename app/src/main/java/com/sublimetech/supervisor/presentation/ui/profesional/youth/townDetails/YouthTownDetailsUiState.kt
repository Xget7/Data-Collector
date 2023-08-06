package com.sublimetech.supervisor.presentation.ui.profesional.youth.townDetails

import com.sublimetech.supervisor.data.model.admin.ProjectDto
import com.sublimetech.supervisor.data.model.locations.TownDto
import com.sublimetech.supervisor.data.model.youtAndWoman.GroupDto
import com.sublimetech.supervisor.domain.model.youthAndWoman.Form

data class YouthTownDetailsUiState(
    var isLoading: Boolean = false,
    val project: ProjectDto = ProjectDto(),
    val town: TownDto = TownDto(),
    val successFormUpload: Boolean = false,
    val workGroups: List<GroupDto> = emptyList(),
    var forms: MutableList<Form> = mutableListOf(),
    val isError: String? = null

)
