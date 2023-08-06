package com.sublimetech.supervisor.data.model.family

data class FamilyMemberDto(
    var id: String = "",
    var familyId: String = "",

    var gender: String = "",
    var name: String = "",
    var documentType: String = "",
    var documentNumber: String = "",
    var dateOfBirth: String = "",
    var specialCondition: String = "",
    var ethnicity: String = "",
    var sexualOrientation: String = "",
    var PSAConsumption: String = "",
    var phoneNumber: String = "",
    var emailAddress: String = "",
    var relationshipWithStudent: String = "",
    var educationLevel: String = "",
    var occupation: String = ""
) {
    fun toMap(): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        map["id"] = id
        map["familyId"] = familyId
        map["gender"] = gender
        map["name"] = name
        map["documentType"] = documentType
        map["documentNumber"] = documentNumber
        map["dateOfBirth"] = dateOfBirth
        map["specialCondition"] = specialCondition
        map["ethnicity"] = ethnicity
        map["sexualOrientation"] = sexualOrientation
        map["PSAConsumption"] = PSAConsumption
        map["phoneNumber"] = phoneNumber
        map["emailAddress"] = emailAddress
        map["relationshipWithStudent"] = relationshipWithStudent
        map["educationLevel"] = educationLevel
        map["occupation"] = occupation
        return map
    }
}