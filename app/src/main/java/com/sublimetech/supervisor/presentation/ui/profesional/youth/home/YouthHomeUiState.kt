package com.sublimetech.supervisor.presentation.ui.profesional.youth.home

import com.sublimetech.supervisor.data.model.locations.TownDto
import com.sublimetech.supervisor.data.model.user.UserDto

data class YouthHomeUiState(
    var isLoading: Boolean = false,
    val user: UserDto? = UserDto(),
    val isError: String? = null,
    val navigate: String? = null,
    val townsList: List<TownDto> = emptyList()
)
