package com.sublimetech.supervisor.data.model.youtAndWoman

import com.sublimetech.supervisor.domain.model.WomanForm.Deliverables
import com.sublimetech.supervisor.domain.model.WomanForm.RepresentativeSignature
import com.sublimetech.supervisor.domain.model.youthAndWoman.Form

data class FormDto(
    var id: String = "",
    var professionalId: String = "",
    var professionalName: String = "",
    var professionalDocumentId: String = "",

    var finished: Boolean = false,

    //NO actualizable
    var startDate: String = "",
    var startTime: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,

    var sessionNumber: Int = 0,

    var groupName: String = "",

    var area: String? = null,


    var attendancesList: MutableMap<String, String> = hashMapOf(),
    var deliverablesList: HashMap<String, Deliverables> = hashMapOf(),
    var representativeSignaturesList: MutableMap<String, RepresentativeSignature> = hashMapOf(), // admin y coso del proyecto
    var developmentEvidenceList: HashMap<String, String> = hashMapOf()
) {

    fun toForm(isLocal: Boolean = false): Form {
        return Form(
            id = id,
            professionalId = professionalId,
            professionalName = professionalName,
            professionalDocumentId = professionalDocumentId,
            finished = finished,
            startDate = startDate,
            startTime = startTime,
            latitude = latitude,
            longitude = longitude,
            sessionNumber = sessionNumber,
            groupName = groupName,
            area = area,
            attendancesList = attendancesList,
            deliverablesList = deliverablesList,
            representativeSignaturesList = representativeSignaturesList,
            developmentEvidenceList = developmentEvidenceList,
            isLocal = isLocal
        )
    }

    fun updateFormToMap(): HashMap<String, Any> {
        return hashMapOf(
            "id" to id,
            "professionalId" to professionalId,
            "professionalName" to professionalName,
            "professionalDocumentId" to professionalDocumentId,
            "finished" to finished,
            "startDate" to startDate,
            "startTime" to startTime,
            "latitude" to latitude,
            "longitude" to longitude,
            "sessionNumber" to sessionNumber,
            "groupName" to groupName,
            "area" to area.orEmpty(),
            "attendancesList" to attendancesList,
            "deliverablesList" to deliverablesList,
            "representativeSignaturesList" to representativeSignaturesList,
            "developmentEvidenceList" to developmentEvidenceList
        )
    }

    fun updateFormImages(): HashMap<String, Any> {
        return hashMapOf(
            "attendancesList" to attendancesList,
            "deliverablesList" to deliverablesList,
            "representativeSignaturesList" to representativeSignaturesList,
            "developmentEvidenceList" to developmentEvidenceList
        )
    }

}
