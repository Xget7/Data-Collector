package com.sublimetech.supervisor.presentation.ui.profesional.family.townDetails

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.sublimetech.supervisor.BuildConfig
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.custom.HistoryFamilyForm
import com.sublimetech.supervisor.presentation.ui.profesional.family.townDetails.components.FamilyCircularProgress
import com.sublimetech.supervisor.presentation.ui.profesional.family.townDetails.components.NewFormFamily
import com.sublimetech.supervisor.presentation.ui.profesional.woman.townDetails.components.TownHeader
import com.sublimetech.supervisor.presentation.utils.Screens
import com.sublimetech.supervisor.ui.theme.Blue
import com.sublimetech.supervisor.ui.theme.Orange
import com.sublimetech.supervisor.ui.theme.Purple
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FamilyTownDetailsScreen(
    navController: NavController,
    vm: FamilyTownDetailsViewModel = hiltViewModel()
) {

    val state by vm.uiState.collectAsState()

    var page = remember {
        mutableStateOf(0)
    }
    val scope = rememberCoroutineScope()


    fun openPdfFile(context: Context, pdfFile: File) {
        val contentUri =
            FileProvider.getUriForFile(context, "${BuildConfig.APPLICATION_ID}.provider", pdfFile)

        val openPdfIntent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(contentUri, "application/pdf")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        if (openPdfIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(openPdfIntent)
        } else {
            Toast.makeText(
                context,
                "No se encontró una aplicación compatible para abrir el PDF",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    val context = LocalContext.current

    val currentPhases by remember {
        derivedStateOf {
            state.families.map { it.visit.currentPhase.toInt() }
        }
    }

    if (state.isLoading) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(color = Orange)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            state.town?.apply {
                image?.let {
                    TownHeader(
                        nombreDelMunicipio = name,
                        beneficiarios = beneficiaries.size,
                        colaboradores = professionalsId.size,
                        talleres = meetings,
                        image = it
                    ) {
                        navController.navigateUp()
                    }
                }
            }

            Column() {

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .padding(horizontal = 90.dp, vertical = 40.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(245.dp)
                    ) {
                        FamilyCircularProgress(
                            percent = if (state.families.isNotEmpty()) {
                                when (page.value) {
                                    (0) -> {
                                        vm.returnProgressFloat(currentPhases, 1)
                                    }

                                    (1) -> {
                                        vm.returnProgressFloat(currentPhases, 2)
                                    }

                                    (2) -> {
                                        vm.returnProgressFloat(currentPhases, 3)
                                    }

                                    else -> 0.0f
                                }
                            } else {
                                0.0f
                            },
                            value = 100,
                            color = when (page.value) {
                                (0) -> Orange
                                (1) -> Purple
                                (2) -> Blue
                                else -> Color.Gray
                            },
                            total = state.project?.familyObjectiveMoments ?: 10,
                            completed = when (page.value) {
                                (0) -> {
                                    (vm.returnProgress(currentPhases, 1))
                                }

                                (1) -> {
                                    (vm.returnProgress(currentPhases, 2))
                                }

                                (2) -> {
                                    (vm.returnProgress(currentPhases, 3))
                                }

                                else -> 0
                            },
                            onBack = {
                                if (page.value >= 1) {
                                    page.value--
                                }
                            },
                            onNext = {
                                if (page.value < 2) {
                                    page.value++

                                }
                            },
                            page = page.value
                        )
                    }

                    NewFormFamily(openForm = {
                        //create new family
                        navController
                            .navigate(Screens.ProfessionalFamilyNewFormScreen.route + "/${vm.projectId.value}")
                    }, openPdf = {
                        scope.launch {
                            vm.getHelperPdfUrl()
                                ?.let { openPdfFile(context, it) }
                        }
                    })
                }


                HistoryFamilyForm(
                    state.families
                ) { family ->
                    navController.navigate(Screens.ProfessionalFamilyUpdateFormScreen.route + "/${family.projectId}/${family.id}")
                }
            }
        }

        if (state.isError != null) {
            Toast.makeText(context, state.isError, Toast.LENGTH_LONG).show()
            vm.clearError()
        }
    }
}