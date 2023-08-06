package com.sublimetech.supervisor.presentation.ui.profesional

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.sublimetech.supervisor.presentation.ui.profesional.family.home.FamilyHomeScreen
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.FamilyNewFormScreen
import com.sublimetech.supervisor.presentation.ui.profesional.family.townDetails.FamilyTownDetailsScreen
import com.sublimetech.supervisor.presentation.ui.profesional.family.updateForm.FamilyUpdateFormScreen
import com.sublimetech.supervisor.presentation.ui.profesional.woman.WomanHomeScreen
import com.sublimetech.supervisor.presentation.ui.profesional.woman.newForm.WomanNewFormScreen
import com.sublimetech.supervisor.presentation.ui.profesional.woman.townDetails.WomanTownDetailsScreen
import com.sublimetech.supervisor.presentation.ui.profesional.woman.updateForm.WomanUpdateFormScreen
import com.sublimetech.supervisor.presentation.ui.profesional.youth.home.YouthHomeScreen
import com.sublimetech.supervisor.presentation.ui.profesional.youth.newForm.YouthNewFormScreen
import com.sublimetech.supervisor.presentation.ui.profesional.youth.townDetails.YouthTownDetailsScreen
import com.sublimetech.supervisor.presentation.ui.profesional.youth.updateForm.YouthUpdateFormScreen
import com.sublimetech.supervisor.presentation.utils.Screens
import com.sublimetech.supervisor.presentation.utils.Screens.ProfessionalFamilyScreen
import com.sublimetech.supervisor.presentation.utils.Screens.ProfessionalWomanScreen
import com.sublimetech.supervisor.presentation.utils.Utils.FAMILY
import com.sublimetech.supervisor.presentation.utils.Utils.WOMAN
import com.sublimetech.supervisor.presentation.utils.Utils.YOUTH


private const val ARG_PARAM1 = "professional"


class ProfessionalFragment : Fragment() {
    private var param1: String? = null


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = ComposeView(requireContext())
        view.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.Default)
            setContent {
                ProfessionalNavHost(arguments?.getString(ARG_PARAM1).toString())
            }
        }
        return view
    }


}


@OptIn(ExperimentalAnimationApi::class)
@androidx.compose.runtime.Composable
fun ProfessionalNavHost(professionalType: String) {

    Log.d("professionalType", professionalType)

    val familyNav = rememberAnimatedNavController()
    val youthNav = rememberAnimatedNavController()
    val womanNav = rememberAnimatedNavController()


    when (professionalType) {
        FAMILY -> {
            AnimatedNavHost(
                navController = familyNav,
                startDestination = ProfessionalFamilyScreen.route,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(
                            durationMillis = 600,
                            easing = LinearOutSlowInEasing
                        )
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(
                            durationMillis = 600,
                            easing = FastOutSlowInEasing
                        )
                    )
                }

            ) {
                composable(
                    ProfessionalFamilyScreen.route,

                    ) {
                    FamilyHomeScreen(familyNav)
                }
                composable(
                    Screens.ProfessionalFamilyTownDetailsScreen.route + "/{projectId}/{townId}",
                ) {
                    FamilyTownDetailsScreen(familyNav)
                }
                composable(
                    Screens.ProfessionalFamilyNewFormScreen.route + "/{projectId}",
                ) {
                    FamilyNewFormScreen(familyNav)

                }
                composable(
                    Screens.ProfessionalFamilyUpdateFormScreen.route + "/{projectId}/{familyId}",
                ) {
                    FamilyUpdateFormScreen(familyNav)

                }


            }
        }

        YOUTH -> {
            AnimatedNavHost(
                navController = youthNav,
                startDestination = Screens.ProfessionalYouthScreen.route,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(
                            durationMillis = 600,
                            easing = FastOutSlowInEasing
                        )
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(
                            durationMillis = 600,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
            ) {
                composable(Screens.ProfessionalYouthScreen.route) {
                    YouthHomeScreen(youthNav)
                }
                composable(
                    Screens.ProfessionalYouthTownDetailsScreen.route + "/{projectId}/{townId}",
                ) {
                    YouthTownDetailsScreen(youthNav)
                }
                composable(
                    Screens.ProfessionalYouthNewFormScreen.route + "/{projectId}/{groupId}/{formNumber}",
                ) {
                    YouthNewFormScreen(youthNav)
                }
                composable(
                    Screens.ProfessionalYouthUpdateFormScreen.route + "/{projectId}/{groupId}/{formId}/{isLocal}",
                ) {
                    YouthUpdateFormScreen(youthNav)
                }

            }
        }

        WOMAN -> {
            AnimatedNavHost(
                navController = womanNav,
                startDestination = ProfessionalWomanScreen.route,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = LinearOutSlowInEasing
                        )
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = LinearOutSlowInEasing
                        )
                    )
                }
            ) {
                composable(
                    ProfessionalWomanScreen.route,
                ) {
                    WomanHomeScreen(womanNav)
                }
                composable(
                    Screens.ProfessionalWomanTownDetailsScreen.route + "/{projectId}/{townId}",
                ) {
                    WomanTownDetailsScreen(womanNav)
                }
                composable(
                    Screens.ProfessionalWomanNewFormScreen.route + "/{projectId}/{groupId}/{formNumber}",
                ) {
                    WomanNewFormScreen(womanNav)
                }
                composable(
                    Screens.ProfessionalWomanUpdateFormScreen.route + "/{projectId}/{groupId}/{formId}/{isLocal}",
                ) {
                    WomanUpdateFormScreen(womanNav)
                }
            }
        }
    }
}

