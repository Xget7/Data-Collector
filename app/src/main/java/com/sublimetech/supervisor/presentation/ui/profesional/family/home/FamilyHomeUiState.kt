package com.sublimetech.supervisor.presentation.ui.profesional.family.home

import com.sublimetech.supervisor.data.model.locations.TownDto
import com.sublimetech.supervisor.data.model.user.UserDto

data class FamilyHomeUiState(
    val isLoading: Boolean = false,
    val isError: String? = null,
    val townsList: List<TownDto> = emptyList(),
    val user: UserDto = UserDto()

)
