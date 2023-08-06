package com.sublimetech.supervisor.data.model.family

import com.sublimetech.supervisor.domain.model.DevelopmentPhoto

data class VisitDto(
    val id: String = "",

    val date: String = "",
    var currentPhase: Float = 0f,
    var followUp: String? = null,

    var guardianSignature: String = "",

    var funDevelopmentPhotos: HashMap<String, DevelopmentPhoto> = hashMapOf(),
    var thematicDevelopmentPhotos: HashMap<String, DevelopmentPhoto> = hashMapOf(),
    var developmentPhotos: HashMap<String, String> = hashMapOf(),


    var professionalSignature1: String? = null,
    var professionalSignature2: String? = "",
    var professionalSignature3: String? = "",

    var assistance: HashMap<String, String> = hashMapOf(),
    var assistance2: HashMap<String, String> = hashMapOf(),
    var assistance3: HashMap<String, String> = hashMapOf(),


    var relationship: FamilyRelationship? = null,
    var feedBack: ProgramFeedback? = null,
    //val members : List<MemberDto> = emptyList(),
) {
    fun toMap(): Map<String, Any?> {


        return mapOf(
            "id" to id,
            "date" to date,
            "currentPhase" to currentPhase,
            "followUp" to followUp,
            "guardianSignature" to guardianSignature,
            "thematicDevelopmentPhotos" to thematicDevelopmentPhotos,
            "funDevelopmentPhotos" to funDevelopmentPhotos,
            "developmentPhotos" to developmentPhotos,
            "professionalSignature1" to professionalSignature1,
            "professionalSignature2" to professionalSignature2,
            "professionalSignature3" to professionalSignature3,
            "assistance" to assistance,
            "assistance2" to assistance2,
            "assistance3" to assistance3,
            "relationship" to relationship?.toMap(),
            "feedBack" to feedBack?.toMap()
        )
    }
}
