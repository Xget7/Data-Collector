package com.sublimetech.supervisor.presentation.ui.profesional.woman.newForm.components

import android.net.Uri
import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import com.sublimetech.supervisor.R
import com.sublimetech.supervisor.core.components.ButtonCustom
import com.sublimetech.supervisor.presentation.utils.Utils.saveImage
import com.sublimetech.supervisor.ui.theme.Orange
import io.ak1.drawbox.DrawBox
import io.ak1.drawbox.rememberDrawController


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FirmasDeRepresentantes(
    representantes: List<String>,
    addToMap: (String, String) -> Unit,
    userFullName: String,
    enabled: Boolean
) {
    var imageFirm by remember { mutableStateOf<Uri?>(null) }
    var firmDialog2 by remember { mutableStateOf(true) }
    var firmDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val controller = rememberDrawController()


    Spacer(modifier = Modifier.height(75.dp))
    Text(
        text = "Firmas",
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    )
    Spacer(modifier = Modifier.height(35.dp))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        representantes.forEach { name ->
            if (firmDialog && firmDialog2) {
                firmDialog2 = false
                Dialog(
                    onDismissRequest = { },
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
                                .size(600.dp)
                        ) {
                            DrawBox(drawController = controller)
                        }
                    }
                }
            }

            if (firmDialog) {
                Dialog(
                    onDismissRequest = {},
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
                                firmDialog = false
                            })
                            Spacer(modifier = Modifier.padding(10.dp))
                            ButtonCustom(
                                value = "Continuar",
                                enabled = enabled,
                                onClick = {
                                    controller.getDrawBoxBitmap()?.let { bitmap ->
                                        val uri = context.saveImage(bitmap)
                                        imageFirm = uri
                                        addToMap(
                                            name,
                                            uri.toString()
                                        )
                                    }
                                    firmDialog = false
                                })
                        }
                    }
                }
            }
            Card(
                elevation = 10.dp,
                shape = RoundedCornerShape(10),
                modifier = Modifier
                    .height(200.dp)
                    .width(400.dp)
                    .padding(bottom = 20.dp)

            ) {
                if (imageFirm != null && name.contains(userFullName)) {
                    Image(
                        painter = rememberAsyncImagePainter(imageFirm),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                if (name.contains(userFullName) && enabled) {
                                    controller.reset()
                                    firmDialog = true
                                }
                            }
                    )

                } else {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                Log.d("name == userfullname", "$name/ == $userFullName")
                                if (name.contains(userFullName) && enabled) {
                                    Log.d("TRUE CONTAINS", "Containts lol ")
                                    controller.reset()
                                    firmDialog = true
                                }
                            }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icono_de_firma),
                            contentDescription = null
                        )
                    }

                }
            }
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.padding(45.dp))
        }

    }
}

