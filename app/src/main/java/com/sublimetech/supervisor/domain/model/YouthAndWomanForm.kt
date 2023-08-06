package com.sublimetech.supervisor.domain.model

data class YouthAndWomanForm(
    val id: String? = null,
    var session: String = "",
    var workGroup: String = "",
    var area: String = "",
    var startDate: String = "",
    var formsGoal: Int = 0,
    var average: Float = 0f
)
