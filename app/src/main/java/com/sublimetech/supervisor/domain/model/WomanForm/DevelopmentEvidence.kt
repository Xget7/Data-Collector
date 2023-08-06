package com.sublimetech.supervisor.domain.model.WomanForm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class DevelopmentEvidence(
    val groupPhoto: MutableState<String> = mutableStateOf(""),
    val selfie: MutableState<String> = mutableStateOf(""),
    val teachingClass: MutableState<String> = mutableStateOf(""),
    var freePhoto: MutableState<String> = mutableStateOf(""),
) {
    fun toMap(): HashMap<String, String> {
        return hashMapOf(
            "groupPhoto" to groupPhoto.value,
            "selfie" to selfie.value,
            "teachingClass" to teachingClass.value,
            "freePhoto" to freePhoto.value
        )
    }

}