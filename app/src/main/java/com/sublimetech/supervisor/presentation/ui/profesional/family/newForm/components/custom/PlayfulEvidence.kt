package com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.custom

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.sublimetech.supervisor.core.components.CameraView
import com.sublimetech.supervisor.domain.model.DevelopmentPhoto
import com.sublimetech.supervisor.ui.theme.Orange
import java.io.File
import java.util.concurrent.Executors

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PlayfulEvidence(
    funDevelopmentPhotos: HashMap<String, DevelopmentPhoto>
) {
    var selected by remember { mutableStateOf("") }
    var cameraDialog by remember { mutableStateOf(false) }
    var cameraOrGaleryDialog by remember { mutableStateOf(false) }
    var visualizadorDeImagen by remember { mutableStateOf(false) }


    val context = LocalContext.current
    val outputDirectory = File(context.filesDir, "")

    val cameraPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted
            cameraDialog = true
        } else {
            // Permission Denied
            cameraDialog = false
            Toast.makeText(
                context,
                "Nececitamos permisos para acceder a tu camara!",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        when (selected) {
            "ludica 1" -> {
                if (funDevelopmentPhotos["1"] != null) {
                    funDevelopmentPhotos["1"]?.uri = uri.toString()
                } else {
                    funDevelopmentPhotos["1"] = DevelopmentPhoto(uri = uri.toString())
                }

                cameraDialog = false
            }

            "ludica 2" -> {
                if (funDevelopmentPhotos["2"] != null) {
                    funDevelopmentPhotos["2"]?.uri = uri.toString()
                } else {
                    funDevelopmentPhotos["2"] = DevelopmentPhoto(uri = uri.toString())
                }

                cameraDialog = false
            }

            "ludica 3" -> {
                if (funDevelopmentPhotos["3"] != null) {
                    funDevelopmentPhotos["3"]?.uri = uri.toString()
                } else {
                    funDevelopmentPhotos["3"] = DevelopmentPhoto(uri = uri.toString())
                }

                cameraDialog = false
            }
        }
        cameraOrGaleryDialog = false
    }


    if (cameraOrGaleryDialog) {
        Dialog(onDismissRequest = { cameraOrGaleryDialog = false }) {
            Card(
                shape = RoundedCornerShape(15),
                backgroundColor = Orange,//Claro,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 60.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Card(
                        shape = RoundedCornerShape(15),
                        elevation = 5.dp
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable {
                                    launcher.launch("*/*")
                                }
                                .padding(20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.PhotoLibrary,
                                tint = Color.Gray,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                            )
                            Text(
                                text = "Abrir Galeria",
                                fontWeight = FontWeight.Bold,
                                color = Color.LightGray,
                                modifier = Modifier
                                    .padding(top = 10.dp)
                            )
                        }
                    }

                    Card(
                        shape = RoundedCornerShape(15),
                        elevation = 5.dp
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable {
                                    when (PackageManager.PERMISSION_GRANTED) {
                                        //Check permission
                                        ContextCompat.checkSelfPermission(
                                            context,
                                            Manifest.permission.CAMERA
                                        ) -> {
                                            // You can use the API that requires the permission.
                                            cameraOrGaleryDialog = false
                                            cameraDialog = true
                                        }

                                        else -> {
                                            // Asking for permission
                                            cameraPermission.launch(Manifest.permission.CAMERA)
                                        }
                                    }

                                }
                                .padding(20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.PhotoCamera,
                                tint = Color.Gray,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                            )
                            Text(
                                text = "Abrir Camara",
                                fontWeight = FontWeight.Bold,
                                color = Color.LightGray,
                                modifier = Modifier
                                    .padding(top = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    if (visualizadorDeImagen) {
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Card(
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .width(750.dp)
                    .height(1000.dp)
            ) {

                Box() {
                    when (selected) {
                        "ludica 1" -> {
                            Image(
                                painter = rememberAsyncImagePainter(funDevelopmentPhotos["1"]?.uri),
                                contentDescription = null
                            )
                        }

                        "ludica 2" -> {
                            Image(
                                painter = rememberAsyncImagePainter(funDevelopmentPhotos["2"]?.uri),
                                contentDescription = null
                            )
                        }

                        "ludica 3" -> {
                            Image(
                                painter = rememberAsyncImagePainter(funDevelopmentPhotos["3"]?.uri),
                                contentDescription = null
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxSize()
                    ) {
                        IconButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .background(Color.White, shape = CircleShape)
                                .size(55.dp)
                                .align(Alignment.TopEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = Orange,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clickable {
                                        visualizadorDeImagen = false
                                    }
                            )
                        }
                        OutlinedButton(
                            onClick = {
                                visualizadorDeImagen = false
                                cameraOrGaleryDialog = true
                            },
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .padding(20.dp)
                                .align(Alignment.BottomCenter)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Camera,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(30.dp)
                                )
                                Spacer(modifier = Modifier.padding(5.dp))
                                Text(
                                    text = "Reemplazar imagen",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.padding(15.dp))
    Column() {

        Text(
            text = "Evidencias de desarrollo de ludicas",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 10.dp)
                .fillMaxWidth()
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(vertical = 30.dp)
                .fillMaxWidth()
        ) {
            if (cameraDialog) {
                Dialog(
                    onDismissRequest = {},
                    properties = DialogProperties(usePlatformDefaultWidth = false)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        CameraView(
                            outputDirectory = outputDirectory,
                            executor = Executors.newSingleThreadExecutor(),
                            onImageCaptured = { uri ->
                                when (selected) {
                                    "ludica 1" -> {
                                        if (funDevelopmentPhotos["1"] != null) {
                                            funDevelopmentPhotos["1"]?.uri = uri.toString()
                                        } else {
                                            funDevelopmentPhotos["1"] =
                                                DevelopmentPhoto(uri = uri.toString())
                                        }
                                        cameraDialog = false
                                    }

                                    "ludica 2" -> {
                                        if (funDevelopmentPhotos["2"] != null) {
                                            funDevelopmentPhotos["2"]?.uri = uri.toString()
                                        } else {
                                            funDevelopmentPhotos["2"] =
                                                DevelopmentPhoto(uri = uri.toString())
                                        }
                                        cameraDialog = false
                                    }

                                    "ludica 3" -> {
                                        if (funDevelopmentPhotos["3"] != null) {
                                            funDevelopmentPhotos["3"]?.uri = uri.toString()
                                        } else {
                                            funDevelopmentPhotos["3"] =
                                                DevelopmentPhoto(uri = uri.toString())
                                        }

                                        cameraDialog = false
                                    }
                                }
                            },
                            onError = { Log.e("kilo", "View error:", it) }
                        )
                    }
                }
            }

            // key(image1Local.value) {
            Card(
                modifier = Modifier
                    .size(165.dp),
                shape = RoundedCornerShape(20),
                elevation = 10.dp
            ) {

                if (funDevelopmentPhotos["1"]?.uri?.isNotBlank() == true) {
                    Log.d("(image1Local.value.isNotBlank()", "IDK")
                    Image(
                        painter = rememberAsyncImagePainter(funDevelopmentPhotos["1"]?.uri),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                selected = "ludica 1"
                                visualizadorDeImagen = true
                            }
                    )

                } else {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                selected = "ludica 1"
                                cameraOrGaleryDialog = true
                            }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Image,
                                tint = Color.LightGray,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                            )
                            Text(
                                text = "Ludica #1",
                                fontWeight = FontWeight.Bold,
                                color = Color.LightGray,
                                modifier = Modifier
                                    .padding(top = 10.dp)
                            )
                        }
                    }
                }


            }
            //}

            Spacer(modifier = Modifier.padding(25.dp))
            Card(
                modifier = Modifier
                    .size(165.dp),
                shape = RoundedCornerShape(20),
                elevation = 10.dp
            ) {
                if (funDevelopmentPhotos["2"]?.uri?.isNotBlank() == true) {
                    Image(
                        painter = rememberAsyncImagePainter(funDevelopmentPhotos["2"]?.uri),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                selected = "ludica 2"
                                visualizadorDeImagen = true
                            }
                    )


                } else {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                selected = "ludica 2"
                                cameraOrGaleryDialog = true
                                //cameraDialog = true
                            }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Image,
                                tint = Color.LightGray,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                            )
                            Text(
                                text = "Ludica #2",
                                fontWeight = FontWeight.Bold,
                                color = Color.LightGray,
                                modifier = Modifier
                                    .padding(top = 10.dp)
                            )
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.padding(25.dp))
            Card(
                modifier = Modifier
                    .size(165.dp),
                shape = RoundedCornerShape(20),
                elevation = 10.dp
            ) {
                if (funDevelopmentPhotos["3"]?.uri?.isNotBlank() == true) {
                    Image(
                        painter = rememberAsyncImagePainter(funDevelopmentPhotos["3"]?.uri),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                selected = "ludica 3"
                                visualizadorDeImagen = true
                            }
                    )

                } else {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                selected = "ludica 3"
                                cameraOrGaleryDialog = true
                            }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Image,
                                tint = Color.LightGray,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                            )
                            Text(
                                text = "Ludica #3",
                                fontWeight = FontWeight.Bold,
                                color = Color.LightGray,
                                modifier = Modifier
                                    .padding(top = 10.dp)
                            )
                        }
                    }
                }

            }

        }
    }


    Spacer(modifier = Modifier.padding(5.dp))

}

