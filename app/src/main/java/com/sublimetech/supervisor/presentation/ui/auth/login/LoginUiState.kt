package com.sublimetech.supervisor.presentation.ui.auth.login

import com.sublimetech.supervisor.domain.model.BasicUserTypes

data class LoginUiState(
    var isAuthenticated: Boolean = false,
    var isError: String? = null,
    var isLoading: Boolean = false,
    var passwordReset: Boolean = false,
    val authUserType: BasicUserTypes = BasicUserTypes(),
    var navigateTo: String? = null
)
