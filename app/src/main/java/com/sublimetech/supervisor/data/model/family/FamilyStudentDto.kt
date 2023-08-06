package com.sublimetech.supervisor.data.model.family

data class FamilyStudentDto(
    var name: String = "",
    var docType: String = "",
    var docNumber: String = "",
    var birthDate: String = "",
    var educationalInstitution: String = "",
    var course: String = "",
    var grade: String = "",
    var shift: String = "",
    var specialCondition: String = "",
    var raceOrEthnicity: String = "",
    var sexualOrientation: String = "",
    var signsOfMistreatment: String = "",
    var signsOfViolence: String = "",
    var cohabitatingFamilyMembers: String = "",
) {
    fun toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["name"] = name
        map["docType"] = docType
        map["docNumber"] = docNumber
        map["birthDate"] = birthDate
        map["educationalInstitution"] = educationalInstitution
        map["course"] = course
        map["grade"] = grade
        map["shift"] = shift
        map["specialCondition"] = specialCondition
        map["raceOrEthnicity"] = raceOrEthnicity
        map["sexualOrientation"] = sexualOrientation
        map["signsOfMistreatment"] = signsOfMistreatment
        map["signsOfViolence"] = signsOfViolence
        map["cohabitatingFamilyMembers"] = cohabitatingFamilyMembers
        return map
    }
}