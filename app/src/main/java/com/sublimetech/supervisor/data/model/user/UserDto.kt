package com.sublimetech.supervisor.data.model.user


data class UserDto(
    var id: String = "",

    var name: String = "",
    var professionalType: String = "",  // "Family Professional" or "Youth Professional" or "Women Professional"
    var userType: String = "", // ADMIN , PROFESSIONAL , SUPERVISOR


    var projects: List<String> = listOf(),


    /*
        Informacion de Usuarios
    */
    val documentNumber: String? = null,
    val fullName: String? = null,
    val birthDate: String? = null,
    val address: String? = null,
    val email: String? = null,
    val professionalTitle: String? = null
) {
    fun toMap(): HashMap<Any, String> {
        return hashMapOf(
            id to "id",
            name to "name",
            professionalType to "profession",
            userType to "userType",
            documentNumber.orEmpty() to "documentNumber",
            projects.orEmpty() to "projects",
            fullName.orEmpty() to "fullName",
            birthDate.orEmpty() to "birthDate",
            address.orEmpty() to "address",
            email.orEmpty() to "email",
            professionalTitle.orEmpty() to "professionalTitle",
        )
    }
}
