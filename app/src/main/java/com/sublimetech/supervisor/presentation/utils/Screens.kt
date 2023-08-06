package com.sublimetech.supervisor.presentation.utils

sealed class Screens(val route: String) {
    object ProfessionalWomanScreen : Screens("professional_woman_screen")
    object ProfessionalFamilyScreen : Screens("professional_family_screen")

    object ProfessionalWomanTownDetailsScreen : Screens("professional_woman_town_details_screen")

    object ProfessionalYouthTownDetailsScreen : Screens("professional_youth_town_details_screen")

    object ProfessionalFamilyTownDetailsScreen : Screens("professional_family_town_details_screen")

    object ProfessionalWomanNewFormScreen : Screens("professional_woman_new_form_screen")

    object ProfessionalYouthNewFormScreen : Screens("professional_youth_new_form_screen")

    object ProfessionalYouthUpdateFormScreen : Screens("professional_youth_update_form_screen")

    object ProfessionalWomanUpdateFormScreen : Screens("professional_woman_update_form_screen")

    object ProfessionalFamilyUpdateFormScreen : Screens("professional_family_update_form_screen")

    object ProfessionalFamilyNewFormScreen : Screens("professional_family_new_form_screen")

    object ProfessionalYouthScreen : Screens("professional_youth_screen")
}
