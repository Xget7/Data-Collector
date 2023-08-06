package com.sublimetech.supervisor.data.model.admin

data class ProgramDto(
    val id: String = "",
    val name: String = "",
    val professionalsIds: List<String> = emptyList(), // program , //tallerista nombre // docmuneto
    val type: String = "", //YOUTH OR WOMAN,
    //SUB COLLECTION
    // val groups : List<String> //Group Dto
)
