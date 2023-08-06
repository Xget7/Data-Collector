package com.sublimetech.supervisor.presentation.ui.profesional.family.updateForm

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sublimetech.supervisor.core.components.SuccessAnimation
import com.sublimetech.supervisor.core.components.TopAppBarCustom
import com.sublimetech.supervisor.core.location.checkAndRequestLocationPermissions
import com.sublimetech.supervisor.data.model.family.FamilyMemberDto
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.PageInfo
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.PageContent
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.custom.CustomFamilyProgress
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.forms.FirstStage
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.forms.SecondStage
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.forms.ThirdStage
import com.sublimetech.supervisor.presentation.utils.Utils
import com.sublimetech.supervisor.ui.theme.LightOrange
import com.sublimetech.supervisor.ui.theme.Orange
import kotlin.math.roundToInt


@SuppressLint("UnrememberedMutableState")
@Composable
fun FamilyUpdateFormScreen(
    navController: NavController,
    vm: FamilyUpdateFormViewModel = hiltViewModel()
) {

    val uiState by vm.uiState.collectAsState()
    val context = LocalContext.current


    val page = rememberSaveable {
        mutableStateOf(0)
    }


    val numeroDeFamiliares = remember {
        derivedStateOf {
            mutableStateOf(1)
        }
    }

    val direction = rememberSaveable { mutableStateOf(true) }

    val totalPages = derivedStateOf {
        numeroDeFamiliares.value.value + 6
    }
    val scrollStateStep2 = rememberScrollState()


    val pageInfo = remember {
        PageInfo(page.value, totalPages.value, {
            direction.value = it
        }, changePage = {

        })
    }

    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            // Use Write
            vm.configureBeforeUpload()
        } else {
            Toast.makeText(
                context,
                "Nececitamos permisos para continuar.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    LaunchedEffect(vm.visitMutable.value.currentPhase) {
        if (vm.visitMutable.value.currentPhase.roundToInt() == 1) {
            vm.insertAttendancePeople()
            page.value = numeroDeFamiliares.value.value + 4
        } else if (vm.visitMutable.value.currentPhase.roundToInt() >= 2) {
            vm.insertAttendancePeople()
            page.value = numeroDeFamiliares.value.value + 5
        }
    }


    if (uiState.isLoading) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(color = Orange)
        }
    } else if (uiState.isSuccessFamilyUpdated) {
        SuccessAnimation(true) {
            navController.navigateUp()
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBarCustom(
                    navigationIcon = Icons.Rounded.ArrowBack,
                    onClickNavigation = {
                        navController.navigateUp()
                    },
                    actionIcon = null,
                    onClickAction = {},
                    title = uiState.project.projectName,
                    showMenu = false,
                    onClickMenu1 = { /*TODO*/ },
                    menuString = ""
                )
            }
        ) { it ->
            it

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CustomFamilyProgress(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(50))
                            .height(14.dp),
                        width = 800.dp,
                        backgroundColor = Color.LightGray,
                        foregroundColor = Brush.horizontalGradient(listOf(LightOrange, Orange)),
                        percent = when (page.value) {
                            (1) -> 0.15f
                            (0) -> 0.05f
                            (2 + numeroDeFamiliares.value.value) -> 0.17f
                            (3 + numeroDeFamiliares.value.value) -> 0.29f
                            (4 + numeroDeFamiliares.value.value) -> 0.48f
                            (5 + numeroDeFamiliares.value.value) -> 0.837f
                            (6 + numeroDeFamiliares.value.value) -> 1f
                            else -> 0.15f
                        }
                    )
                }



                Box() {
                    FirstStage(
                        familyId = vm.familyId.value,
                        studentInfo = vm.studentInfo,
                        familyRelationship = vm.familyRelationship,
                        relativesInfo = vm.relativesInfo,
                        visit = vm.visitMutable,
                        page = page,
                        direction = direction,
                        attendancePeople = vm.attendacePeople,
                        onSetRelatives = {
                            Log.d("onSetRelatives", it.toString())
                            numeroDeFamiliares.value.value = it
                            if (it != 1) {
                                vm.relativesInfo.clear()
                                for (i in 1..it) {
                                    Log.d("onSetRelatives ADD", i.toString())

                                    vm.relativesInfo.add(
                                        mutableStateOf(
                                            FamilyMemberDto()
                                        )
                                    )
                                }
                            }
                            Log.d("RelaivesSize", vm.relativesInfo.size.toString())
                        }
                    ) {
                        vm.visitMutable.value.currentPhase = 1f
                        checkAndRequestLocationPermissions(
                            context,
                            Utils.filesPermissions,
                            launcherMultiplePermissions
                        ) {
                            vm.configureBeforeUpload()

                        }
                    }


                    PageContent(
                        changePage = { page.value = it },
                        pageNumber = numeroDeFamiliares.value.value + 4,
                        right = direction.value,
                        page = page.value,
                        changeDirection = { directionBool ->
                            direction.value = directionBool
                        },
                        numeroDePaginas = numeroDeFamiliares.value.value + 3
                    ) { pageInfo ->
                        SecondStage(
                            onClick = {
                                vm.visitMutable.value.currentPhase = 2f

                                checkAndRequestLocationPermissions(
                                    context,
                                    Utils.filesPermissions,
                                    launcherMultiplePermissions
                                ) {
                                    vm.configureBeforeUpload()

                                }
                            },
                            visit = vm.visitMutable,
                            students = vm.attendacePeople,
                            assistanceHashMap = vm.visitMutable.value.assistance2,
                            removeFromMap = { key ->
                                vm.visitMutable.value.assistance2.remove(key)
                            },
                            addToMap = { key, uri ->
                                vm.visitMutable.value.assistance2[key] = uri
                            },
                            scrollState = scrollStateStep2,
                            updateSignature = {
                                vm.visitMutable.value.professionalSignature2 = it
                            }
                        )
                    }

                    PageContent(
                        changePage = { page.value = it },
                        pageNumber = numeroDeFamiliares.value.value + 5,
                        right = direction.value,
                        page = page.value,
                        changeDirection = { directionBool ->
                            direction.value = directionBool
                        },
                        numeroDePaginas = numeroDeFamiliares.value.value + 1
                    ) { pageInfo ->
                        ThirdStage(
                            programFeedback = vm.feedback
                        ) {
                            vm.visitMutable.value.currentPhase = 3f

                            checkAndRequestLocationPermissions(
                                context,
                                Utils.filesPermissions,
                                launcherMultiplePermissions
                            ) {
                                vm.configureBeforeUpload()

                            }

                        }
                    }


                    if (page.value != totalPages.value - 1) {
                        Card(
                            shape = CircleShape,
                            elevation = 1.dp,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(30.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null, tint = Orange,
                                modifier = Modifier
                                    .clickable {
                                        page.value++
                                        pageInfo.changePage(page.value)
                                        pageInfo.changeDirection(true)
                                        direction.value = true
                                        if (page.value == numeroDeFamiliares.value.value + 3) {
                                            vm.insertAttendancePeople()
                                        }


                                    }
                                    .padding(10.dp)
                                    .size(40.dp)
                            )
                        }
                    }


                    if (page.value != 0) {
                        Card(
                            shape = CircleShape,
                            elevation = 1.dp,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(30.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null, tint = Orange,
                                modifier = Modifier
                                    .clickable {
                                        if (isInPage(
                                                page.value,
                                                numeroDeFamiliares.value.value + 4
                                            )
                                        ) {

                                        }
                                        page.value--
                                        pageInfo.changePage(page.value)
                                        pageInfo.changeDirection(false)
                                        direction.value = false


                                    }
                                    .rotate(180f)
                                    .padding(10.dp)
                                    .size(40.dp)
                            )
                        }
                    }

                    if (uiState.isError != null) {
                        Toast.makeText(context, uiState.isError, Toast.LENGTH_LONG).show()
                    }
                }
            }


        }

    }

}

fun isInPage(actual: Int, desired: Int) = actual == desired