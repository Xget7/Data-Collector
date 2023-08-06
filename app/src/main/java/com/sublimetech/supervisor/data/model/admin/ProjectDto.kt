package com.sublimetech.supervisor.data.model.admin

import com.sublimetech.supervisor.domain.model.WomanForm.ProjectBasicInfo

data class ProjectDto(
    val id: String = "",
    val adminId: String = "",
    val supervisorId: String = "",
    val townId: String? = null,


    val professionals: List<String> = emptyList(),

    //SUB COLLECTIONS
    //val family: List<String> = emptyList(),
    //val programs: List<String> = emptyList(),

    val projectName: String = "",
    val agreement: String = "",
    val agreementDate: String = "",
    val agreementPlace: String = "",
    val objective: String = "",

    val familyObjectiveMoments: Int = 0,

    val progressTown: Int? = null,
    val reach: Int? = null
) {
    fun toProjectBasicInfo(): ProjectBasicInfo {
        return ProjectBasicInfo(
            name = projectName,
            agreement = agreement,
            agreementDate = agreementDate,
            agreementPlace = agreementPlace,
            _object = objective,
        )
    }
}
