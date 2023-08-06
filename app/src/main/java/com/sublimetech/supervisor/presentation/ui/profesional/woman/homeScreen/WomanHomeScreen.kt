package com.sublimetech.supervisor.presentation.ui.profesional.woman

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sublimetech.supervisor.core.components.TopAppBarProfesionalHomeScreen
import com.sublimetech.supervisor.presentation.ui.profesional.woman.homeScreen.components.TopScreen
import com.sublimetech.supervisor.presentation.ui.profesional.woman.homeScreen.components.TownList
import com.sublimetech.supervisor.presentation.ui.profesional.youth.home.YouthHomeViewModel
import com.sublimetech.supervisor.presentation.utils.Screens
import com.sublimetech.supervisor.ui.theme.Orange

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WomanHomeScreen(
    navController: NavHostController,
    vm: YouthHomeViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        Log.d("profesisonalType", "womanHomeScreen")
    }

    val context = LocalContext.current

    val uiState by vm.uiState.collectAsState()
    val selectedName = remember {
        mutableStateOf("BOLIVAR")
    }

    var showMenu by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBarProfesionalHomeScreen(
                actionIcon = Icons.Outlined.AccountCircle,
                onClickAction = { showMenu = !showMenu },
                showMenu = showMenu,
                onClickMenu = vm::signOut,
                menuString = "Cerrar sesion"
            )
        }
    ) {

        if (uiState.isLoading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = Orange)

            }
        } else if (uiState.isError != null) {
            Toast.makeText(context, uiState.isError, Toast.LENGTH_LONG).show()
            vm.clearError()
        } else {
            Column {
                TopScreen(
                    selectedName.value,
                    onNameSelected = { name -> selectedName.value = name }
                )

                TownList(
                    uiState.townsList.filter { it.departament == selectedName.value }
                ) {
                    navController.navigate("${Screens.ProfessionalWomanTownDetailsScreen.route}/${it[0]}/${it[1]}")
                }

            }
        }


    }
}


