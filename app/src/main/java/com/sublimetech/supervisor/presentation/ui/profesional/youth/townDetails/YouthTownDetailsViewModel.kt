package com.sublimetech.supervisor.presentation.ui.profesional.youth.townDetails

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sublimetech.supervisor.domain.providers.FilesProvider
import com.sublimetech.supervisor.domain.repositories.images.OfflineFormsRepository
import com.sublimetech.supervisor.domain.useCases.forms.FormsUseCases
import com.sublimetech.supervisor.domain.useCases.groups.GroupsUseCases
import com.sublimetech.supervisor.domain.useCases.projects.ProjectUseCases
import com.sublimetech.supervisor.domain.useCases.storage.FirebaseStorageUseCases
import com.sublimetech.supervisor.presentation.utils.Utils.HELPER_PDF
import com.sublimetech.supervisor.presentation.utils.Utils.YOUTH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class YouthTownDetailsViewModel @Inject constructor(
    private val projectUseCases: ProjectUseCases,
    private val formsUseCases: FormsUseCases,
    private val offlineRepo: OfflineFormsRepository,
    private val groupsUseCases: GroupsUseCases,
    private val files: FilesProvider,
    private val storageUseCases: FirebaseStorageUseCases,
    val sharedPreferences: SharedPreferences,
    savedStateHandle: SavedStateHandle

) : ViewModel() {

    private val _uiState = MutableStateFlow(YouthTownDetailsUiState())
    val uiState = _uiState.asStateFlow()

    val projectId = mutableStateOf("")
    val townId = mutableStateOf("")
    var pdfHelper: File? = null
    var openPdfHelper: MutableState<Boolean> = mutableStateOf(false)
    var counter = 0

    init {
        savedStateHandle.get<String>("projectId")?.let {
            projectId.value = it
        }
        savedStateHandle.get<String>("townId")?.let {
            townId.value = it
        }

        if (projectId.value.isNotEmpty()) {
            getProjectData(projectId.value)
        }

    }


    private fun getProjectData(id: String) {
        loading(true)
        val mResult = projectUseCases.getProjectUseCase.invoke(id)
        mResult.onEach { result ->
            if (result.isSuccess) {
                _uiState.update {
                    _uiState.value.copy(project = result.getOrThrow())
                }
                if (townId.value.isNotEmpty()) {
                    getTownFromId(townId.value)
                    getGroups(id)
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


    private fun getTownFromId(townId: String) {
        val result = projectUseCases.getTownById.invoke(townId)
        result.onEach { r ->
            if (r.isSuccess) {
                _uiState.update {
                    _uiState.value.copy(town = r.getOrThrow())
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


    private fun getGroups(projectId: String) {
        viewModelScope.launch {
            val result = groupsUseCases.getGroupListUseCase.invoke(projectId, YOUTH)
            if (result.isSuccess) {
                val _result = result.getOrThrow()
                _uiState.update {
                    _uiState.value.copy(
                        workGroups = _result
                    )
                }
                if (_result.isNotEmpty()) {
                    getForms(projectId)
                } else {
                    _uiState.update {
                        _uiState.value.copy(
                            isLoading = false
                        )
                    }
                }
                return@launch
            } else {
                _uiState.update {
                    _uiState.value.copy(
                        isError = result.exceptionOrNull()?.localizedMessage
                            ?: "Error obteniendo grupos.", isLoading = false
                    )
                }
            }

        }
    }

    private fun getForms(projectId: String) {
        _uiState.value.workGroups.forEach { id ->
            val _result = formsUseCases.getFormsSnapshotUseCase.invoke(projectId, YOUTH, id.id)
            _result.onEach { result ->
                if (result.isSuccess) {
                    counter++
                    val newList = result.getOrThrow().toMutableList()
                    _uiState.update {
                        _uiState.value.copy().apply {
                            if (counter > workGroups.size) {
                                val deleteForms = forms.filter { it.groupName == id.name }
                                deleteForms.forEach {
                                    forms.remove(it)
                                }
                            }
                            forms.addAll(newList)
                            //if (!fromLocal) getLocalForms()
                            isLoading = false
                        }
                    }

                } else {
                    _uiState.update {
                        _uiState.value.copy(
                            isError = result.exceptionOrNull()?.localizedMessage
                                ?: "Error obteniendo formularios.", isLoading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }


    suspend fun getHelperPdfUrl(): File? {
        return withContext(viewModelScope.coroutineContext) {
            val url = files.getPdfUrl()
            pdfHelper = files.createOrGetFile("helper.pdf")
            if (url == sharedPreferences.getString(HELPER_PDF, null)) {
                Log.d("initHelperPdf", "??? 1 ")
                pdfHelper
            } else {
                if (getHelperPdf(url)) pdfHelper else null
            }
        }
    }

    private suspend fun getHelperPdf(url: String): Boolean {
        return withContext(Dispatchers.IO) {
            Log.d("initHelperPdf", "INVOKING")
            val result = storageUseCases
                .downloadFileUseCase
                .invoke("app", "contrato-de-desarrollo-de-software.pdf", pdfHelper!!)

            if (result.isFailure) {
                Log.d("initHelperPdf", "ERROR ${result.exceptionOrNull()} ")
                _uiState.update {
                    _uiState.value.copy(isError = result.exceptionOrNull()?.localizedMessage)
                }
                return@withContext false
            } else if (result.isSuccess) {
                Log.d("initHelperPdf", "SUCESS")

                FileOutputStream(pdfHelper).use { outputStream ->
                    outputStream.write(result.getOrThrow())
                    sharedPreferences.edit().putString(HELPER_PDF, url).apply()
                }

                return@withContext true
            }
            false
        }
    }


    private fun loading(isLoading: Boolean) {
        _uiState.update {
            _uiState.value.copy(isLoading = isLoading)
        }
    }

    fun clearError() {
        _uiState.update {
            _uiState.value.copy(isLoading = false, isError = null)
        }
    }


}