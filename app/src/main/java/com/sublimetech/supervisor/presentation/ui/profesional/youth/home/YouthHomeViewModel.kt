package com.sublimetech.supervisor.presentation.ui.profesional.youth.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sublimetech.supervisor.domain.useCases.projects.ProjectUseCases
import com.sublimetech.supervisor.domain.useCases.user.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YouthHomeViewModel @Inject constructor(
    private val userUseCase: UserUseCases,
    private val projectUseCases: ProjectUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(YouthHomeUiState())
    val uiState = _uiState.asStateFlow()

    private val currentUserId: String? = Firebase.auth.currentUser?.uid

    init {
        currentUserId?.apply {
            getUserData(this)
            getTownsFromUser(this)
        }
    }


    private fun getUserData(uid: String) {
        viewModelScope.launch {
            loading(true)
            val result = userUseCase.getUserUseCase.invoke(uid)
            if (result.isSuccess) {
                _uiState.update {
                    _uiState.value.copy(user = result.getOrNull())
                }

                return@launch
            }
            _uiState.update {
                _uiState.value.copy(
                    isError = result.exceptionOrNull()?.localizedMessage,
                    isLoading = false
                )
            }
        }
    }


    private fun getTownsFromUser(userId: String) {
        val result = projectUseCases.getTownsById.invoke(userId)
        result.onEach { r ->
            if (r.isSuccess) {
                _uiState.update {
                    _uiState.value.copy(townsList = r.getOrDefault(listOf()), isLoading = false)
                }
            } else {
                _uiState.update {
                    _uiState.value.copy(
                        isError = r.exceptionOrNull()?.localizedMessage
                            ?: "Error obteniendo departamentos.", isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loading(isLoading: Boolean) {
        _uiState.update {
            _uiState.value.copy(isLoading = isLoading)
        }
    }


    fun signOut() {
        Firebase.auth.signOut()
    }

    fun clearError() {
        _uiState.update {
            _uiState.value.copy(isLoading = false, isError = null)
        }
    }


}