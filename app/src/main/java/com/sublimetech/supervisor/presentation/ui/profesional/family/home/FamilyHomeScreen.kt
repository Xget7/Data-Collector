package com.sublimetech.supervisor.presentation.ui.profesional.family.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sublimetech.supervisor.core.components.TopAppBarProfesionalHomeScreen
import com.sublimetech.supervisor.presentation.ui.profesional.woman.homeScreen.components.TopScreen
import com.sublimetech.supervisor.presentation.ui.profesional.woman.homeScreen.components.TownList
import com.sublimetech.supervisor.presentation.utils.Screens

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FamilyHomeScreen(
    navController: NavHostController,
    vm: FamilyHomeViewModel = hiltViewModel()
) {

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
    ) { p ->

        if (uiState.isLoading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.isError != null) {
            Toast.makeText(context, uiState.isError, Toast.LENGTH_LONG).show()
            vm.clearError()
        } else {
            Column(
                Modifier.padding(p)
            ) {
                TopScreen(
                    selectedName.value,
                    onNameSelected = { name -> selectedName.value = name }
                )

                TownList(
                    uiState.townsList.filter { it.departament == selectedName.value }
                ) {
                    navController.navigate("${Screens.ProfessionalFamilyTownDetailsScreen.route}/${it[0]}/${it[1]}")
                }

            }
        }


    }

}