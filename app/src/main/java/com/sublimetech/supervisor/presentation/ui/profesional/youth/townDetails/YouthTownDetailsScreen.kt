package com.sublimetech.supervisor.presentation.ui.profesional.youth.townDetails

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.sublimetech.supervisor.BuildConfig
import com.sublimetech.supervisor.core.location.checkAndRequestLocationPermissions
import com.sublimetech.supervisor.presentation.ui.profesional.woman.townDetails.components.CircularProgress
import com.sublimetech.supervisor.presentation.ui.profesional.woman.townDetails.components.HistoryForm
import com.sublimetech.supervisor.presentation.ui.profesional.woman.townDetails.components.NewFormAndOpenPdf
import com.sublimetech.supervisor.presentation.ui.profesional.woman.townDetails.components.TownHeader
import com.sublimetech.supervisor.presentation.utils.Screens
import com.sublimetech.supervisor.ui.theme.*
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun YouthTownDetailsScreen(
    navController: NavController,
    viewModel: YouthTownDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    val pagerState = rememberPagerState(
        initialPage = 0
    )
    val scope = rememberCoroutineScope()


    fun openPdfFile(context: Context, pdfFile: File) {
        val contentUri =
            FileProvider.getUriForFile(context, "${BuildConfig.APPLICATION_ID}.provider", pdfFile)

        val openPdfIntent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(contentUri, "application/pdf")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        Log.d("pdfUri", pdfFile.toUri().toString())

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
    val groupName = remember {
        derivedStateOf {
            when (pagerState.currentPage) {
                0 -> "Grupo A"
                1 -> "Grupo B"
                2 -> "Grupo C"
                3 -> "Grupo D"
                else -> null
            }
        }
    }


    val currentGroup by remember {
        derivedStateOf {
            state.workGroups.find { it.name == groupName.value }
        }
    }

    var permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )

    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            // Use Write
            scope.launch {
                viewModel.getHelperPdfUrl()
                    ?.let { openPdfFile(context, it) }
            }
        } else {
            Log.d("permisos", permissionsMap.toString())
            Toast.makeText(
                context,
                "Nececitamos permisos para descargar la guia!",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    if (state.isLoading) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            state.town.image?.let {
                TownHeader(
                    nombreDelMunicipio = state.town.name,
                    beneficiarios = state.town.beneficiaries.size,
                    colaboradores = state.town.professionalsId.size,
                    talleres = state.town.meetings,
                    image = it
                ) {
                    navController.navigateUp()
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
                        key(state.forms) {
                            CircularProgress(
                                porcentage = if (groupName.value != null) {
                                    if (state.forms.isNotEmpty()) {
                                        val completedClasses =
                                            state.forms.count { it.groupName == groupName.value && it.finished }
                                        val formsGoal = currentGroup?.formsGoal ?: 1
                                        completedClasses.toFloat() / formsGoal.toFloat()
                                    } else {
                                        0.0f
                                    }
                                } else {
                                    0.1f
                                },
                                valor = 100,
                                color = when (pagerState.currentPage) {
                                    (0) -> Orange
                                    (1) -> Purple
                                    (2) -> Blue
                                    (3) -> Verde
                                    else -> Color.Gray
                                }
                            )
                        }
                    }

                    NewFormAndOpenPdf(pagerState = pagerState, openForm = {
                        //MEJORAR ESTO
                        val groupNames = listOf("Grupo A", "Grupo B", "Grupo C", "Grupo D")
                        val currentGroupId = state.workGroups
                            .firstOrNull {
                                it.name in groupNames && it.id.isNotBlank() && pagerState.currentPage == groupNames.indexOf(
                                    it.name
                                )
                            }
                            ?.id ?: ""


                        Log.d("UseCaseNEWFORM", state.forms.toString())
                        val maxSessionNumberForm =
                            state.forms.filter { it.groupName == groupName.value }
                                .maxByOrNull { it.sessionNumber }
                        val maxSessionNumber = maxSessionNumberForm?.sessionNumber ?: 0
                        navController.navigate(Screens.ProfessionalYouthNewFormScreen.route + "/${state.project.id}/$currentGroupId/${maxSessionNumber}")

                    }, openPdf = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            permissions.plus(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                        }
                        checkAndRequestLocationPermissions(
                            context,
                            permissions,
                            launcherMultiplePermissions
                        ) {
                            scope.launch {
                                Log.d("executed", "1")
                                viewModel.getHelperPdfUrl()
                                    ?.let {
                                        openPdfFile(context, it)
                                    }
                            }
                        }
                    })
                }



                key(state.forms) {
                    HistoryForm(
                        page = pagerState.currentPage,
                        state.forms.filter { it.groupName == groupName.value }
                    ) { formId, isLocal ->
                        navController.navigate(Screens.ProfessionalYouthUpdateFormScreen.route + "/${state.project.id}/${currentGroup?.id}/${formId}/$isLocal")
                    }
                }
            }
        }



        if (state.isError != null) {
            Toast.makeText(context, state.isError, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    BackHandler() {
        viewModel.openPdfHelper.value = false
    }


}

