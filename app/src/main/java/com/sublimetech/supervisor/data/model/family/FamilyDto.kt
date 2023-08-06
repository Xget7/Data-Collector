package com.sublimetech.supervisor.data.model.family

data class FamilyDto(
    var id: String = "",
    var professionalId: String? = null, //clave foranea

    var projectId: String = "",

    var name: String = "",
    var address: String = "",
    var createdTime: Long = 0L,

    var members: List<FamilyMemberDto> = listOf(),
    var student: FamilyStudentDto? = null,
    var visit: VisitDto = VisitDto()// 80%
) {
    fun toMap(): HashMap<String, Any> {
        val map = hashMapOf<String, Any>()
        map["id"] = id
        map["professionalId"] = professionalId.orEmpty()
        map["projectId"] = projectId
        map["name"] = name
        map["createdTime"] = createdTime
        map["address"] = address
        map["members"] = members.map { it.toMap() }
        map["student"] = student?.toMap().orEmpty()
        map["visit"] = visit.toMap()
        return map
    }
}