package com.sublimetech.supervisor.presentation.ui.profesional.woman.townDetails

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sublimetech.supervisor.domain.model.youthAndWoman.Form
import com.sublimetech.supervisor.domain.providers.FilesProvider
import com.sublimetech.supervisor.domain.repositories.images.OfflineFormsRepository
import com.sublimetech.supervisor.domain.useCases.forms.FormsUseCases
import com.sublimetech.supervisor.domain.useCases.groups.GroupsUseCases
import com.sublimetech.supervisor.domain.useCases.projects.ProjectUseCases
import com.sublimetech.supervisor.domain.useCases.storage.FirebaseStorageUseCases
import com.sublimetech.supervisor.presentation.utils.Utils.HELPER_PDF
import com.sublimetech.supervisor.presentation.utils.Utils.WOMAN
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
class WomanTownDetailsViewModel @Inject constructor(
    private val projectUseCases: ProjectUseCases,
    private val formsUseCases: FormsUseCases,
    private val offlineRepo: OfflineFormsRepository,
    private val groupsUseCases: GroupsUseCases,
    private val files: FilesProvider,
    private val storageUseCases: FirebaseStorageUseCases,
    val sharedPreferences: SharedPreferences,
    savedStateHandle: SavedStateHandle

) : ViewModel() {

    private val _uiState = MutableStateFlow(WomanTownDetailsUiState())
    val uiState = _uiState.asStateFlow()

    val projectId = mutableStateOf("")
    val townId = mutableStateOf("")
    var pdfHelper: File? = null
    var openPdfHelper: MutableState<Boolean> = mutableStateOf(false)
    var counter = 0

    val oldFilteredForms = mutableListOf<Form>()

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
            val result = groupsUseCases.getGroupListUseCase.invoke(projectId, WOMAN)
            if (result.isSuccess) {
                _uiState.update {
                    _uiState.value.copy(
                        workGroups = result.getOrThrow()
                    )
                }
                getForms(projectId)
                //tengo que darle prioridad al local , si se encuentra uno igual se tiene que sobreescribir el guardado en firebase local

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
            val _result = formsUseCases.getFormsSnapshotUseCase.invoke(projectId, WOMAN, id.id)
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
                                ?: "Error obteniendo grupos.", isLoading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

//    private fun getLocalForms() {
//        val oldPendingForms = mutableListOf<Form>()
//        offlineRepo.getPendingForms().onEach { result ->
//            val filteredForms =
//                result.filter { it.programType == WOMAN }.map { it.formWithUris }
//            oldPendingForms.addAll(filteredForms)
//            Log.d("PendingForms", filteredForms.size.toString())
//            if (filteredForms.isNotEmpty()) {
//                _uiState.update {
//                    _uiState.value.copy().apply {
//                        oldPendingForms.forEach { oldPendingForm ->
//                            forms.remove(oldPendingForm)
//                        }
//
//                        for (i in 0 until filteredForms.size - 1) {
//                            if (filteredForms[i].id != filteredForms[i + 1].id) {
//                                forms.add(filteredForms[i])
//                                Log.d("EqualForm", "NOT EQUAL")
//                            } else if (filteredForms[i].isLocal) {
//                                Log.d("EqualForm", "LOCAL")
//                                forms.add(filteredForms[i])
//                                forms.remove(filteredForms[i + 1])
//                            } else {
//                                Log.d("EqualForm", " NOT LOCAL")
//                                forms.add(filteredForms[i + 1])
//                                forms.remove(filteredForms[i])
//                            }
//                        }
//
////                        forms.addAll(filteredForms)
//
//                        isLoading = false
//                    }
//                }
//            } else {
//                _uiState.update {
//                    _uiState.value.copy().apply {
//                        oldPendingForms.forEach { oldPendingForm ->
//                            forms.remove(oldPendingForm)
//                        }
//                        getForms(projectId.value)
//
//                        isLoading = false
//                    }
//                }
//            }
//        }.launchIn(viewModelScope)
//    }


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
                    _uiState.value.copy(
                        isError = result.exceptionOrNull()?.localizedMessage,
                        isLoading = false
                    )
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
        Log.d("uisState", _uiState.value.toString())
    }

    fun clearError() {
        _uiState.update {
            _uiState.value.copy(isLoading = false, isError = null)
        }
    }


}