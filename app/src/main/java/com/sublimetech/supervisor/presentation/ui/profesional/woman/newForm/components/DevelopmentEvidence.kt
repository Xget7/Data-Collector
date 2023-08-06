package com.sublimetech.supervisor.presentation.ui.profesional.woman.newForm.components

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.sublimetech.supervisor.core.components.CameraView
import com.sublimetech.supervisor.domain.model.WomanForm.DevelopmentEvidence
import com.sublimetech.supervisor.ui.theme.Orange
import java.io.File
import java.util.concurrent.Executors

@Composable
fun DevelopmentEvidence(
    photosList: DevelopmentEvidence,
    updateList: (DevelopmentEvidence) -> Unit,
    outputDirectory: File,
    enabled: Boolean
) {
    var fotoSeleccionada by remember { mutableStateOf("") }
    var cameraView by remember { mutableStateOf(false) }
    var cameraPermission by remember { mutableStateOf(false) }
    var context = LocalContext.current

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
//test

    if (cameraView) {
        when (PackageManager.PERMISSION_GRANTED) {
            //Check permission
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) -> {
                // You can use the API that requires the permission.
                cameraView = enabled
                cameraPermission = true
            }

            else -> {
                // Asking for permission
                launcher.launch(Manifest.permission.CAMERA)
            }
        }
    }
    if (cameraView && cameraPermission) {

        CameraView(
            outputDirectory = outputDirectory,
            executor = Executors.newSingleThreadExecutor(),
            onImageCaptured = { uri ->
                when (fotoSeleccionada) {
                    "grupal" -> {
                        updateList(
                            photosList.copy(
                                groupPhoto = mutableStateOf(uri.toString())
                            )
                        )
                        cameraView = false
                    }

                    "selfi" -> {
                        updateList(
                            photosList.copy(
                                selfie = mutableStateOf(uri.toString())
                            )
                        )
                        cameraView = false
                    }

                    "dictando clase" -> {
                        updateList(
                            photosList.copy(
                                teachingClass = mutableStateOf(uri.toString())
                            )
                        )
                        cameraView = false
                    }

                    "libre" -> {
                        updateList(
                            photosList.copy(
                                freePhoto = mutableStateOf(uri.toString())
                            )
                        )
                        cameraView = false
                    }
                }
            },
            onError = { Log.e("kilo", "View error:", it) }
        )
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Evidencias de desarrollo",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (photosList.groupPhoto.value == "") {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = 10.dp,
                    backgroundColor = MaterialTheme.colors.surface,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(300.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable {
                                fotoSeleccionada = "grupal"
                                cameraView = enabled
                            }
                            .fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(20.dp),
                            tint = MaterialTheme.colors.onBackground
                        )
                        Text(
                            text = "Foto grupal",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onBackground,
                            fontSize = 25.sp
                        )
                    }
                }
            } else {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = 10.dp,
                    backgroundColor = MaterialTheme.colors.surface,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(300.dp)
                ) {
                    Box() {
                        Image(
                            painter = rememberAsyncImagePainter(photosList.groupPhoto.value),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(10.dp)
                                .align(Alignment.BottomEnd)
                        ) {

                            Spacer(modifier = Modifier.padding(5.dp))
                            Card(
                                shape = CircleShape,
                                backgroundColor = Orange,
                                modifier = Modifier
                                    .size(50.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier
                                        .clickable {
                                            fotoSeleccionada = "grupal"
                                            cameraView = enabled
                                        }
                                        .fillMaxSize()
                                        .padding(10.dp)
                                )
                            }
                        }
                    }
                }
            }

            if (photosList.selfie.value == "") {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = 10.dp,
                    backgroundColor = MaterialTheme.colors.surface,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(300.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable {
                                fotoSeleccionada = "selfi"
                                cameraView = enabled
                            }
                            .fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(20.dp),
                            tint = MaterialTheme.colors.onBackground
                        )
                        Text(
                            text = "Selfi",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onBackground,
                            fontSize = 25.sp
                        )
                    }
                }
            } else {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = 10.dp,
                    backgroundColor = MaterialTheme.colors.surface,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(300.dp)
                ) {
                    Box() {
                        Image(
                            painter = rememberAsyncImagePainter(photosList.selfie.value),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(10.dp)
                                .align(Alignment.BottomEnd)
                        ) {

                            Spacer(modifier = Modifier.padding(5.dp))
                            Card(
                                shape = CircleShape,
                                backgroundColor = Orange,
                                modifier = Modifier
                                    .size(50.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier
                                        .clickable {
                                            fotoSeleccionada = "selfi"
                                            cameraView = enabled
                                        }
                                        .fillMaxSize()
                                        .padding(10.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(15.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (photosList.teachingClass.value == "") {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = 10.dp,
                    backgroundColor = MaterialTheme.colors.surface,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(300.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable {
                                fotoSeleccionada = "dictando clase"
                                cameraView = enabled
                            }
                            .fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(20.dp),
                            tint = MaterialTheme.colors.onBackground
                        )
                        Text(
                            text = "Dictando clase",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onBackground,
                            fontSize = 25.sp
                        )
                    }
                }
            } else {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = 10.dp,
                    backgroundColor = MaterialTheme.colors.surface,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(300.dp)
                ) {
                    Box() {
                        Image(
                            painter = rememberAsyncImagePainter(photosList.teachingClass.value),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(10.dp)
                                .align(Alignment.BottomEnd)
                        ) {

                            Spacer(modifier = Modifier.padding(5.dp))
                            Card(
                                shape = CircleShape,
                                backgroundColor = Orange,
                                modifier = Modifier
                                    .size(50.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier
                                        .clickable {
                                            fotoSeleccionada = "dictando clase"
                                            cameraView = enabled
                                        }
                                        .fillMaxSize()
                                        .padding(10.dp)
                                )
                            }
                        }
                    }
                }
            }

            if (photosList.freePhoto.value == "") {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = 10.dp,
                    backgroundColor = MaterialTheme.colors.surface,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(300.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable {
                                fotoSeleccionada = "libre"
                                cameraView = enabled
                            }
                            .fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(20.dp),
                            tint = MaterialTheme.colors.onBackground
                        )
                        Text(
                            text = "Fotografia libre",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onBackground,
                            fontSize = 25.sp
                        )
                    }
                }
            } else {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = 10.dp,
                    backgroundColor = MaterialTheme.colors.surface,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(300.dp)
                ) {
                    Box() {
                        Image(
                            painter = rememberAsyncImagePainter(photosList.freePhoto.value),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(10.dp)
                                .align(Alignment.BottomEnd)
                        ) {

                            Spacer(modifier = Modifier.padding(5.dp))
                            Card(
                                shape = CircleShape,
                                backgroundColor = Orange,
                                modifier = Modifier
                                    .size(50.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier
                                        .clickable {
                                            fotoSeleccionada = "libre"
                                            cameraView = enabled

                                        }
                                        .fillMaxSize()
                                        .padding(10.dp)
                                )
                            }
                        }
                    }
                }
            }


        }
    }
}