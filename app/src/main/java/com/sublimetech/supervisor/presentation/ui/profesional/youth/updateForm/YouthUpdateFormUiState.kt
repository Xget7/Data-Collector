package com.sublimetech.supervisor.presentation.ui.profesional.youth.updateForm

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.sublimetech.supervisor.data.model.admin.ProjectDto
import com.sublimetech.supervisor.data.model.user.UserDto
import com.sublimetech.supervisor.data.model.youtAndWoman.GroupDto
import com.sublimetech.supervisor.domain.model.WomanForm.Deliverables

data class YouthUpdateFormUiState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val successFormUpload: Boolean = false,
    val isError: String? = null,
    val representatives: SnapshotStateList<String> = mutableStateListOf(),
    var deliverablesList: SnapshotStateList<Deliverables> = mutableStateListOf(),
    val project: ProjectDto? = null,
    val group: GroupDto? = null,
    val user: UserDto? = null
)
