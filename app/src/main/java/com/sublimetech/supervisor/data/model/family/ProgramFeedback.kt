package com.sublimetech.supervisor.data.model.family

data class ProgramFeedback(
    var programOpinion: String = "",
    var favoriteProgramAspect: String = "",
    var programLessonsLearned: String = "",
    var programParticipationDesire: String = "",
    var programNewFeatureRequests: String = "",
    var programImprovementSuggestions: String = "",
    var anticipatedFamilyChanges: String = "",
    var messageToMayor: String = "",
    var programExpectationFulfillment: String = "",
    var neighborhoodCommonProblem: String = "",
    var familyLifeImprovementAspect: String = "",
    var familyWishForImprovement: String = ""
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "programOpinion" to programOpinion,
            "favoriteProgramAspect" to favoriteProgramAspect,
            "programLessonsLearned" to programLessonsLearned,
            "programParticipationDesire" to programParticipationDesire,
            "programNewFeatureRequests" to programNewFeatureRequests,
            "programImprovementSuggestions" to programImprovementSuggestions,
            "anticipatedFamilyChanges" to anticipatedFamilyChanges,
            "messageToMayor" to messageToMayor,
            "programExpectationFulfillment" to programExpectationFulfillment,
            "neighborhoodCommonProblem" to neighborhoodCommonProblem,
            "familyLifeImprovementAspect" to familyLifeImprovementAspect,
            "familyWishForImprovement" to familyWishForImprovement
        )
    }

}

