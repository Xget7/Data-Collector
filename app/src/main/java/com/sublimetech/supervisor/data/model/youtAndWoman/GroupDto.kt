package com.sublimetech.supervisor.data.model.youtAndWoman


data class GroupDto(
    val id: String = "",
    val name: String = "",
    val project: String = "",
    val actualSession: Int = 0,
    val students: List<StudentDto> = listOf(),
    //lista de classes
    var formsGoal: Int = 0,
    val formsList: List<String> = emptyList()
) {


}