package com.sublimetech.supervisor.presentation.ui.profesional.woman.newForm

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.sublimetech.supervisor.core.components.ButtonCustom
import com.sublimetech.supervisor.core.components.CameraView
import com.sublimetech.supervisor.core.components.SuccessAnimation
import com.sublimetech.supervisor.core.components.TopAppBarCustom
import com.sublimetech.supervisor.core.location.checkAndRequestLocationPermissions
import com.sublimetech.supervisor.domain.model.WomanForm.Deliverables
import com.sublimetech.supervisor.domain.model.WomanForm.ProjectBasicInfo
import com.sublimetech.supervisor.domain.model.WomanForm.RepresentativeSignature
import com.sublimetech.supervisor.presentation.ui.profesional.woman.newForm.components.*
import com.sublimetech.supervisor.presentation.utils.Utils.deliverableTypesListWoman
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID
import java.util.concurrent.Executors

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun WomanNewFormScreen(
    navController: NavController,
    viewModel: WomanNewFormViewModel = hiltViewModel(),
) {


    val uiState by viewModel.uiState.collectAsState()

    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    val outputDirectory = File(context.filesDir, "")

    var openDialog by remember { mutableStateOf(false) }
    var animationDialog by remember { mutableStateOf(false) }

    var derivable by remember {
        mutableStateOf(Deliverables())
    }


    //maybe can be put in viewmodel
    val derivableTypeList by remember {
        mutableStateOf(
            deliverableTypesListWoman
        )
    }

    var infoDeEntregables by remember { mutableStateOf(false) }
    var cameraView by remember { mutableStateOf(false) }

    //camera permissions
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted
            cameraView = true
        } else {
            // Permission Denied
            cameraView = false
            Toast.makeText(
                context,
                "Nececitamos permisos para acceder a tu camara!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            // Use location
            viewModel.getLocation(true)
        } else {
            Toast.makeText(
                context,
                "Nececitamos permisos para acceder a tu localizacion!",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    val pagerState = rememberPagerState(
        initialPage = 0
    )




    Scaffold(
        topBar = {
            TopAppBarCustom(
                navigationIcon = Icons.Rounded.ArrowBack,
                onClickNavigation = {
                    navController.navigateUp()
                },
                actionIcon = Icons.Default.Info,
                onClickAction = {
                    openDialog = true
                    coroutine.launch {
                        delay(10)
                        animationDialog = true
                    }
                },
                title = uiState.group?.project,
                showMenu = false,
                onClickMenu1 = { /*TODO*/ },
                menuString = ""
            )

        }
    ) { padding ->
        padding
        if (uiState.isLoading) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.successFormUpload) {
            SuccessAnimation(true) {
                navController.navigateUp()
            }
        } else {

            // Dialogo de informcacion adicional referente al contrato y contexto del formato a diligenciar
            viewModel.currentForm.value.let {
                DialogMoreInfo(
                    openDialog = openDialog,
                    animationDialog = animationDialog,
                    form = it,
                    project = uiState.project?.toProjectBasicInfo() ?: ProjectBasicInfo()
                ) {
                    openDialog = false
                    animationDialog = false
                }
            }

            if (infoDeEntregables) {
                InfoEntregable(
                    derivableTypeList,
                    count = derivable.quantity,
                    onCountChange = {
                        derivable = derivable.copy(quantity = it)
                    },

                    closeDialog = {
                        infoDeEntregables = false
                    },
                    opcionalValue = derivable.derivable,
                    onOpcionalChange = { derivable = derivable.copy(derivable = it) }
                ) {
                    infoDeEntregables = false
                    if (derivable.id == "") {
                        val newId = UUID.randomUUID().toString()
                        derivable = derivable.copy(id = newId)
                        viewModel.deliverablesData.value.deliverablesList[newId] = derivable
                        uiState.deliverablesList.add(derivable)
                        Log.d("NeWWW Deliverable ", derivable.toString())
                    } else {
                        uiState.deliverablesList.remove(uiState.deliverablesList.first { it.id == derivable.id })
                        viewModel.deliverablesData.value.deliverablesList[derivable.id] = derivable
                        uiState.deliverablesList.add(derivable)

                        Log.d("NOt New Deliverable ", derivable.toString())
                    }

                }

            }

            if (cameraView) {
                CameraView(
                    outputDirectory = outputDirectory,
                    executor = Executors.newSingleThreadExecutor(),
                    onImageCaptured = { uri ->
                        derivable = derivable.copy(image = uri.toString())
                        Log.d("testDerivable", derivable.toString())
                        coroutine.launch {
                            delay(100)
                            pagerState.scrollToPage(viewModel.deliverablesData.value.deliverablesList.size)
                            delay(100)
                            cameraView = false
                            delay(500)
                            infoDeEntregables = true
                        }
                    },
                    onError = { Log.e("kilo", "View error:", it) }
                )
            }




            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState(), enabled = true),
            ) {
                CaruselDeFotos(
                    listaDeEntregables = uiState.deliverablesList,
                    pagerState = pagerState,
                    openCamera = {
                        when (PackageManager.PERMISSION_GRANTED) {
                            //Check permission
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            ) -> {
                                // You can use the API that requires the permission.
                                cameraView = true
                                derivable = Deliverables()

                            }

                            else -> {
                                // Asking for permission
                                launcher.launch(Manifest.permission.CAMERA)
                            }
                        }
                    },
                    onClickEdit = { ent ->
                        derivable = ent
                        infoDeEntregables = true
                    },
                    onClickDelete = { del ->
                        viewModel.deliverablesData.value.deliverablesList.remove(del.id)
                        viewModel.updateDeliverables(del)
                    },
                    onChangePhoto = { del ->
                        cameraView = true
                        derivable = del
                        infoDeEntregables = false

                    }
                )
                Divider(
                    modifier = Modifier.padding(50.dp)
                )
                DevelopmentEvidence(
                    outputDirectory = outputDirectory,
                    photosList = viewModel.deliverablesData.value.developmentEvidence.value,
                    updateList = {
                        viewModel.deliverablesData.value.developmentEvidence.value = it

                    },
                    enabled = true
                )
                Divider(
                    modifier = Modifier.padding(50.dp)
                )
                AttendanceTaking(
                    students = viewModel.deliverablesData.value.attendees,
                    removeFromMap = { name ->
                        viewModel.deliverablesData.value.attendanceList.remove(name)
                    },
                    addToMap = { name, image ->
                        viewModel.deliverablesData.value.attendanceList[name] = image.toString()
                    },
                    isEnabled = true
                )
                Divider(
                    modifier = Modifier.padding(50.dp)
                )
                FirmasDeRepresentantes(
                    representantes = uiState.representatives,
                    userFullName = uiState.userData?.fullName ?: "Actual Representante",
                    addToMap = { name, uri ->
                        viewModel.deliverablesData.value.representativeSignaturesList.add(
                            RepresentativeSignature(
                                signatureUrl = uri,
                                signatureNameAndId = name,
                                signatureType = "PROFESSIONAL"
                            )
                        )
                    },
                    enabled = true
                )
                Spacer(modifier = Modifier.padding(20.dp))
                ButtonCustom(
                    value = "Enviar Formato",
                    onClick = {
                        checkAndRequestLocationPermissions(
                            context,
                            permissions,
                            launcherMultiplePermissions
                        ) {
                            Log.d(
                                "representativeList",
                                viewModel.deliverablesData.value.representatives.toString()
                            )
                            viewModel.getLocation(true)
                        }
                    }, enabled = true
                )
                Spacer(modifier = Modifier.padding(20.dp))

            }


            if (uiState.isError != null) {
                Toast.makeText(context, uiState.isError, Toast.LENGTH_LONG).show()
                viewModel.clearError()
            }

        }
    }


}



