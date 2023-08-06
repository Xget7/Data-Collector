package com.sublimetech.supervisor.presentation.ui.profesional.family.newForm

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.sublimetech.supervisor.core.components.SuccessAnimation
import com.sublimetech.supervisor.core.components.TopAppBarCustom
import com.sublimetech.supervisor.core.location.checkAndRequestLocationPermissions
import com.sublimetech.supervisor.data.model.family.FamilyMemberDto
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.*
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.custom.CustomFamilyProgress
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.forms.FirstStage
import com.sublimetech.supervisor.presentation.utils.Utils.filesPermissions
import com.sublimetech.supervisor.ui.theme.LightOrange
import com.sublimetech.supervisor.ui.theme.Orange


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun FamilyNewFormScreen(
    navController: NavController,
    vm: FamilyNewFormViewModel = hiltViewModel()
) {

    val state by vm.uiState.collectAsState()

    //total number of pages 6


    var page = remember { mutableStateOf(0) }
    val numeroDeFamiliares = remember {
        derivedStateOf {
            mutableStateOf(1)
        }
    }

    var direction = remember { mutableStateOf(true) }

    val totalPages = derivedStateOf {
        numeroDeFamiliares.value.value + 4
    }
    val context = LocalContext.current


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

            Log.d("familyRelationShip", vm.familyRelationship.toString())
            vm.createFamilyAndUpload()
        } else {
            Toast.makeText(
                context,
                "Nececitamos permisos para continuar.",
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
            CircularProgressIndicator(color = Orange)
        }
    } else if (state.isSuccessFamilySaved) {
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
                    onClickAction =
                    {

                    },
                    title = state.project.projectName,
                    showMenu = false,
                    onClickMenu1 = {
                        navController.navigateUp()
                    },
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
                            (2 + numeroDeFamiliares.value.value) -> 0.25f
                            (3 + numeroDeFamiliares.value.value) -> 0.25f
                            (4 + numeroDeFamiliares.value.value) -> 0.38f
                            (5 + numeroDeFamiliares.value.value) -> 0.737f
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
                            numeroDeFamiliares.value.value = it
                            if (it != 1) {
                                vm.relativesInfo.clear()
                                for (i in 1..it) {
                                    vm.relativesInfo.add(
                                        mutableStateOf(
                                            FamilyMemberDto()
                                        )
                                    )
                                }
                            } else {
                                val temp = vm.relativesInfo.first()
                                vm.relativesInfo.clear()
                                vm.relativesInfo.add(temp)
                            }

                            Log.d("RelaivesSize", vm.relativesInfo.size.toString())
                        }
                    ) {
                        vm.visitMutable.value.currentPhase = 1f
                        checkAndRequestLocationPermissions(
                            context,
                            filesPermissions,
                            launcherMultiplePermissions
                        ) {
                            Log.d("familyRelationShip", vm.familyRelationship.toString())
                            vm.createFamilyAndUpload()
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

                    if (state.isError != null) {
                        Toast.makeText(context, state.isError, Toast.LENGTH_LONG).show()
                    }
                }
            }


        }
    }


}


data class PageInfo(
    val page: Int,
    val pagesNumber: Int,
    val changeDirection: (Boolean) -> Unit,
    val changePage: (Int) -> Unit,
)





