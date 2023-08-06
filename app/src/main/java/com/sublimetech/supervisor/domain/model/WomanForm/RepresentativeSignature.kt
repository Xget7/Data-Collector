package com.sublimetech.supervisor.domain.model.WomanForm

data class RepresentativeSignature(
    val id: String = "",
    val signatureUrl: String = "",
    val signatureNameAndId: String = "",
    val signatureType: String = "" //PROFESSIONAL , ADMIN , SUPERVISOR
)
