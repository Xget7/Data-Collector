package com.sublimetech.supervisor.domain.model.WomanForm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.sublimetech.supervisor.data.model.youtAndWoman.StudentDto

data class DeliverablesData(
    var deliverablesList: HashMap<String, Deliverables> = hashMapOf(), //uplpoad images
    var developmentEvidence: MutableState<DevelopmentEvidence> = mutableStateOf(DevelopmentEvidence()), //upload images
    val attendanceList: MutableMap<String, String> = mutableMapOf(),
    var representativeSignaturesList: MutableList<RepresentativeSignature> = mutableListOf(), //upload images
    val attendees: MutableList<StudentDto> = mutableListOf(),
    val representatives: MutableList<String> = mutableListOf()
)
