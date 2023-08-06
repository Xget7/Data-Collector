package com.sublimetech.supervisor.presentation.ui.profesional.family.townDetails

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sublimetech.supervisor.domain.providers.FilesProvider
import com.sublimetech.supervisor.domain.useCases.family.FamiliesUseCases
import com.sublimetech.supervisor.domain.useCases.projects.ProjectUseCases
import com.sublimetech.supervisor.domain.useCases.storage.FirebaseStorageUseCases
import com.sublimetech.supervisor.presentation.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class FamilyTownDetailsViewModel @Inject constructor(
    val familyUseCase: FamiliesUseCases,
    val projectUseCases: ProjectUseCases,
    private val files: FilesProvider,
    private val storageUseCases: FirebaseStorageUseCases,
    val sharedPreferences: SharedPreferences,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(FamilyTownDetailsUiState())
    val uiState = _uiState.asStateFlow()

    val projectId = mutableStateOf("")
    val townId = mutableStateOf("")
    var pdfHelper: File? = null

    init {
        savedStateHandle.get<String>("projectId")?.let {
            projectId.value = it
            getProject(it)
        }

        savedStateHandle.get<String>("townId")?.let {
            townId.value = it
            loading(true)
            if (townId.value.isNotEmpty()) {
                getTownFromId(townId.value)
            }
        }


    }


    private fun getProject(id: String) {
        val mResult = projectUseCases.getProjectUseCase.invoke(id)
        mResult.onEach { result ->
            if (result.isSuccess) {
                _uiState.update {
                    _uiState.value.copy(project = result.getOrThrow())
                }
                getFamilies()
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

    private fun getFamilies() {
        val result = familyUseCase.getFamiliesUseCase.invoke(projectId.value)
        result.onEach { r ->
            if (r.isSuccess) {
                _uiState.update {
                    _uiState.value.copy(families = r.getOrThrow(), isLoading = false)
                }
            } else {
                _uiState.update {
                    _uiState.value.copy(isError = r.exceptionOrNull()?.localizedMessage)
                }
            }
        }.launchIn(viewModelScope)

    }


    suspend fun getHelperPdfUrl(): File? {
        return withContext(viewModelScope.coroutineContext) {
            val url = files.getPdfUrl()
            if (url == sharedPreferences.getString(Utils.HELPER_PDF, null)) {
                Log.d("initHelperPdf", "??? 1 ")
                pdfHelper = files.createOrGetFile("help.pdf")
                pdfHelper
            } else {
                pdfHelper = files.createOrGetFile("help.pdf")
                if (getHelperPdf(url)) pdfHelper else null
            }
        }
    }

    private suspend fun getHelperPdf(url: String): Boolean {
        return withContext(Dispatchers.IO) {
            val result = storageUseCases
                .downloadFileUseCase
                .invoke("app", "contrato-de-desarrollo-de-software.pdf", pdfHelper!!)

            if (result.isFailure) {
                _uiState.update {
                    _uiState.value.copy(isError = result.exceptionOrNull()?.localizedMessage)
                }
                return@withContext false
            } else if (result.isSuccess) {

                FileOutputStream(pdfHelper).use { outputStream ->
                    outputStream.write(result.getOrThrow())
                }
                sharedPreferences.edit().putString(Utils.HELPER_PDF, url).apply()

                return@withContext true
            }
            false
        }
    }

    fun returnProgress(currentPhases: List<Int?>, equal: Int): Int {
        var result = 0
        for (moment in currentPhases) {
            if (moment != null) {
                if (moment >= equal) {
                    result++
                }
            }
        }
        return result
    }

    fun returnProgressFloat(currentPhases: List<Int?>, equal: Int): Float {
        var result = 0.0f
        for (moment in currentPhases) {
            if (moment != null) {
                if (moment >= equal) {
                    result++
                }

            }
        }
        val total = _uiState.value.project?.familyObjectiveMoments
        return result / (total?.toFloat() ?: 10f)
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