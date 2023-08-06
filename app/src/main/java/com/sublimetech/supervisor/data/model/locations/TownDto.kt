package com.sublimetech.supervisor.data.model.locations

data class TownDto(
    val id: String = "",
    val projectId: String = "",
    val professionalsId: List<String> = emptyList(),


    var name: String = "",
    var beneficiaries: List<String> = listOf(),
    var image: String? = "",
    var date: String? = "",
    val meetings: Int = 0,
    val departament: String = ""
)
