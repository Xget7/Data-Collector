package com.sublimetech.supervisor.data.model.youtAndWoman

data class StudentDto(
    val id: String? = null,
    val groupId: String? = null,
    val name: String = "",
    val documentId: String = "",
    val age: Int? = null,
    val gender: String = ""
) {
    fun toMap(): HashMap<String, Any> {
        val map = HashMap<String, Any>()
        map["id"] = id.orEmpty()
        map["groupId"] = groupId.orEmpty()
        map["name"] = name
        map["documentId"] = documentId
        map["age"] = age ?: -1
        map["gender"] = gender
        return map
    }
}
