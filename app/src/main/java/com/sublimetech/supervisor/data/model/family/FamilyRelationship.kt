package com.sublimetech.supervisor.data.model.family

data class FamilyRelationship(
    var haveDinnerTogether: String = "",
    var showAffection: String = "",
    var showDedication: String = "",
    var communicateImportance: String = "",
    var whatYouLike: String = "",
    var whatYouWouldChange: String = "",
    var happiestMoment: String = "",
    var mostLikedQuality: String = "",
    var respectIdentities: String = "",
    var isYourFamilyHappy: String = "",
    var feelProud: String = "",
    var frequencyOfViolenceOrMistreatment: String = "",
    var howToResolveDifficulties: String = "",
    var topicsForOrientation: String = "",
    var describeFamilyRelationship: String = "",
    var professionalObservations: String = "",
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "haveDinnerTogether" to haveDinnerTogether,
            "showAffection" to showAffection,
            "showDedication" to showDedication,
            "communicateImportance" to communicateImportance,
            "whatYouLike" to whatYouLike,
            "whatYouWouldChange" to whatYouWouldChange,
            "happiestMoment" to happiestMoment,
            "mostLikedQuality" to mostLikedQuality,
            "respectIdentities" to respectIdentities,
            "isYourFamilyHappy" to isYourFamilyHappy,
            "feelProud" to feelProud,
            "frequencyOfViolenceOrMistreatment" to frequencyOfViolenceOrMistreatment,
            "howToResolveDifficulties" to howToResolveDifficulties,
            "topicsForOrientation" to topicsForOrientation,
            "describeFamilyRelationship" to describeFamilyRelationship,
            "professionalObservations" to professionalObservations,
        )
    }

}
