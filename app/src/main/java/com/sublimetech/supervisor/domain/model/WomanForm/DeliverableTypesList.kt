package com.sublimetech.supervisor.domain.model.WomanForm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class DeliverableTypesList(
    val deliverableTypes: MutableList<String> = mutableListOf(),
    val deliverablesList: MutableList<String> = mutableListOf(),
    var deliverableType: MutableState<String> = mutableStateOf(""),
    val foodList: MutableList<String> = mutableListOf(),
    val equipmentList: MutableList<String> = mutableListOf(),
    val instrumentList: MutableList<String> = mutableListOf(),
    val materialList: MutableList<String> = mutableListOf(),
    val uniformList: MutableList<String> = mutableListOf(),
    val toolList: MutableList<String> = mutableListOf(),
    var deliverable: MutableState<String> = mutableStateOf(""),
    val onDeliverableTypeSelected: (String) -> Unit = { deliverableType.value = it },
    val onDeliverableSelected: (String) -> Unit = { deliverable.value = it }
)