package com.sublimetech.supervisor.presentation.ui.profesional.youth.newForm

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.sublimetech.supervisor.data.model.admin.ProjectDto
import com.sublimetech.supervisor.data.model.user.UserDto
import com.sublimetech.supervisor.data.model.youtAndWoman.FormDto
import com.sublimetech.supervisor.data.model.youtAndWoman.GroupDto
import com.sublimetech.supervisor.domain.model.WomanForm.Deliverables

data class YouthNewFormUiState(
    val isLoading: Boolean = false,
    val isError: String? = null,
    val project: ProjectDto? = null,
    val userData: UserDto? = null,
    val successFormUpload: Boolean = false,
    val representatives: SnapshotStateList<String> = mutableStateListOf(),
    var deliverablesList: SnapshotStateList<Deliverables> = mutableStateListOf(),
    var form: FormDto? = null,
    val group: GroupDto? = null

)
