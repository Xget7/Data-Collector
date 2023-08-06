package com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.custom

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.sublimetech.supervisor.R
import com.sublimetech.supervisor.core.components.ButtonCustom
import com.sublimetech.supervisor.core.components.CameraView
import com.sublimetech.supervisor.data.model.youtAndWoman.StudentDto
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.PageInfo
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.FirstMomentTitle
import com.sublimetech.supervisor.presentation.ui.profesional.woman.newForm.components.AttendanceTaking
import com.sublimetech.supervisor.presentation.utils.Utils.saveImage
import com.sublimetech.supervisor.ui.theme.Orange
import io.ak1.drawbox.DrawBox
import io.ak1.drawbox.DrawController
import io.ak1.drawbox.rememberDrawController
import java.io.File
import java.util.concurrent.Executors

@Composable
fun DevEvidenceAndAssistant(
    assistanceHashMap: HashMap<String, String>,
    photosMap: HashMap<String, String>,
    outputDirectory: File,
    onSaveForm: (String) -> Unit,
    drawController: DrawController = rememberDrawController(),
    pageInfo: PageInfo,
    attendancePeople: MutableList<StudentDto>
) {

    var imageFirm by remember { mutableStateOf<Uri?>(null) }

    var cameraView by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }


    var currentEvidence by remember { mutableStateOf(0) }

    val c = LocalContext.current

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
                c,
                "Nececitamos permisos para acceder a tu camara!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    if (cameraView) {
        CameraView(
            outputDirectory = outputDirectory,
            executor = Executors.newSingleThreadExecutor(),
            onImageCaptured = { uri ->
                photosMap["$currentEvidence"] = uri.toString()
                cameraView = false
            },
            onError = {
                errorMsg = "Ocurrio un error con tu camara!"
            }
        )
    }


    if (showDialog) {
        FirmDialogComp(
            drawController,
            onChangeFirmDialog = { showDialog = it }
        ) { imageFirm = it }
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White,
        elevation = 10.dp,
        modifier = Modifier
            .padding(50.dp)
            .fillMaxSize()
    ) {
        Box {
            Column(
                modifier = Modifier
                    .padding(end = 50.dp, start = 50.dp, bottom = 6.dp)
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                ) {

                    Row(
                        modifier = Modifier
                            .padding(end = 2.dp, start = 2.dp, bottom = 6.dp, top = 6.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FirstMomentTitle()
                        Spacer(modifier = Modifier.width(260.dp))

                        Text(
                            text = "${pageInfo.page + 1}/${pageInfo.pagesNumber}",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(30.dp)
                        )
                    }


                    Text(
                        text = "Evidencia de Desarrollo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        EvidenceCardPhoto(
                            photosMap.values.firstOrNull() ?: "",
                            num = 1,
                            onClicked = {
                                when (PackageManager.PERMISSION_GRANTED) {
                                    //Check permission
                                    ContextCompat.checkSelfPermission(
                                        c,
                                        Manifest.permission.CAMERA
                                    ) -> {
                                        // You can use the API that requires the permission.
                                        currentEvidence = 1
                                        cameraView = true
                                    }

                                    else -> {
                                        // Asking for permission
                                        launcher.launch(Manifest.permission.CAMERA)
                                    }
                                }

                            })
                        Spacer(modifier = Modifier.width(34.dp))
                        EvidenceCardPhoto(
                            photosMap.getOrDefault("2", ""),
                            num = 2,
                            onClicked = {
                                when (PackageManager.PERMISSION_GRANTED) {
                                    //Check permission
                                    ContextCompat.checkSelfPermission(
                                        c,
                                        Manifest.permission.CAMERA
                                    ) -> {
                                        // You can use the API that requires the permission.
                                        currentEvidence = 2
                                        cameraView = true

                                    }

                                    else -> {
                                        // Asking for permission
                                        launcher.launch(Manifest.permission.CAMERA)
                                    }
                                }
                            })
                    }
                    Spacer(modifier = Modifier.height(65.dp))
                    Divider(color = Color.LightGray)
                    Spacer(modifier = Modifier.height(65.dp))

                    key(attendancePeople) {
                        AttendanceTaking(
                            students = attendancePeople,
                            removeFromMap = { key ->
                                assistanceHashMap.remove(key)
                            },
                            addToMap = { key, value ->
                                assistanceHashMap[key] = value.toString()
                            },
                            attendancesList = assistanceHashMap,
                            true,
                            horizontalPadding = 10.dp
                        )
                    }


                    Spacer(modifier = Modifier.height(70.dp))

                    Card(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(10),
                        modifier = Modifier
                            .height(200.dp)
                            .width(400.dp)
                            .padding(bottom = 20.dp)

                    ) {
                        Image(
                            painter = if (imageFirm != null) rememberAsyncImagePainter(imageFirm) else painterResource(
                                id = R.drawable.icono_de_firma
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    //drawController.reset()
                                    showDialog = true
                                }
                        )
                    }
                    Spacer(modifier = Modifier.height(50.dp))



                    ButtonCustom(
                        value = "Guardar",
                        onClick = {
                            onSaveForm(imageFirm.toString())
                        },
                        enabled = true,
                        verticalPadding = 0.dp
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

            }


        }
    }

    BackHandler() {
        cameraView = false
    }

    if (errorMsg.isNotBlank()) {
        Toast.makeText(c, errorMsg, Toast.LENGTH_LONG).show()
        errorMsg = ""
    }


}

@Composable
fun EvidenceCardPhoto(photoUriOrUrl: String, onClicked: () -> Unit, num: Int) {

    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = 10.dp,
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .padding(10.dp)
            .size(250.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable {
                    onClicked()
                }
                .fillMaxSize()
        ) {
            if (photoUriOrUrl.isEmpty()) {
                Spacer(modifier = Modifier.height(20.dp))
                Icon(
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(10.dp),
                    tint = Color.LightGray
                )
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = "Evidencia #$num",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    fontSize = 22.sp
                )
            } else {
                Image(
                    painter = rememberAsyncImagePainter(photoUriOrUrl),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            }


        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FirmDialogComp(
    controller: DrawController,
    onChangeFirmDialog: (Boolean) -> Unit,
    enabled: Boolean = true,
    onSave: (Uri) -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(true) {
        //controller.reset()
        onChangeFirmDialog(false)
        onChangeFirmDialog(true)

    }

    Dialog(
        onDismissRequest = {
            onChangeFirmDialog(false)
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Card(
                shape = RoundedCornerShape(10),
                modifier = Modifier
                    .height(300.dp)
                    .width(600.dp)
            ) {
                Canvas(
                    modifier = Modifier
                        .padding(80.dp)
                        .fillMaxWidth()
                ) {
                    val canvasWidth = size.width
                    drawLine(
                        start = Offset(x = canvasWidth, y = 200f),
                        end = Offset(x = 0f, y = 200f),
                        color = Orange
                    )
                }
                DrawBox(drawController = controller)
            }

            Spacer(modifier = Modifier.padding(30.dp))

            Row {
                ButtonCustom(value = "Cancelar", enabled = true, onClick = {
                    controller.reset()
                    onChangeFirmDialog(false)
                })
                Spacer(modifier = Modifier.padding(10.dp))
                ButtonCustom(
                    value = "Continuar",
                    enabled = enabled,
                    onClick = {
                        controller.getDrawBoxBitmap()?.let { bitmap ->
                            context.saveImage(bitmap)?.let { onSave(it) }
                        }
                        onChangeFirmDialog(false)
                    })
            }
        }
    }
}

@Preview
@Composable
fun DevEvidenceAndAssistantPrev() {
//    DevEvidenceAndAssistant(
//        mutableListOf<String>(), {},
//        File.createTempFile("dsadads", "dsadads"),
//        drawController = rememberDrawController(),
//        onSaveForm = {}, pageInfo = PageInfo(1, 2, {}, {})
//    )
}