package com.sublimetech.supervisor.domain.model.youthAndWoman

import com.sublimetech.supervisor.data.model.youtAndWoman.FormDto
import com.sublimetech.supervisor.domain.model.WomanForm.Deliverables
import com.sublimetech.supervisor.domain.model.WomanForm.RepresentativeSignature

data class Form(
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
    var representativeSignaturesList: MutableMap<String, RepresentativeSignature> = hashMapOf(),
    var developmentEvidenceList: HashMap<String, String> = hashMapOf(),

    // Nuevas propiedades específicas del dominio
    var isLocal: Boolean = false, // Indica si el formulario se obtuvo localmente
) {
    fun toFormDto(): FormDto {
        return FormDto(
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

            )
    }
}