package com.sublimetech.supervisor.presentation.ui.profesional.woman.newForm.components

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sublimetech.supervisor.core.components.ButtonCustom
import com.sublimetech.supervisor.data.model.youtAndWoman.StudentDto
import com.sublimetech.supervisor.ui.theme.Orange
import com.sublimetech.supervisor.ui.theme.Verde
import io.ak1.drawbox.DrawBox
import io.ak1.drawbox.rememberDrawController
import java.io.File


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AttendanceTaking(
    students: List<StudentDto>,
    removeFromMap: (String) -> Unit,
    addToMap: (String, Uri) -> Unit,
    attendancesList: MutableMap<String, String> = mutableMapOf(),
    isEnabled: Boolean,
    horizontalPadding: Dp = 70.dp
) {
    Card(
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.background(MaterialTheme.colors.background)) {
            var save by remember { mutableStateOf(false) }

            AssistantTopList {
                save = true
            }

            students.forEach { student ->
                if (student.name != "null") {

                    var switch by remember {
                        Log.d("key ", student.name + "$attendancesList")
                        mutableStateOf(attendancesList.containsKey(student.name))
                    }
                    var userHasSignature by remember {
                        mutableStateOf(attendancesList[student.name]?.contains("https://") ?: false)
                    }
                    var contentFirm by remember { mutableStateOf(userHasSignature) }
                    var openDialog2 by remember { mutableStateOf(true) }
                    var openDialog by remember { mutableStateOf(false) }
                    var imageFirm by remember { mutableStateOf<Uri?>(null) }
                    val context = LocalContext.current


                    val controller = rememberDrawController()


                    ////////////////////////////////////////////////////////////////////////////////////
                    // Ignore !

                    if (openDialog && openDialog2) {
                        openDialog2 = false
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

                    // Ignore ¡
                    ////////////////////////////////////////////////////////////////////////////////////

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = student.name + student.documentId,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .weight(9f)
                        )
                        Switch(
                            checked = switch,
                            enabled = isEnabled,
                            onCheckedChange = {
                                switch = !switch
                                removeFromMap(student.name)
                                contentFirm = false

                            },
                            modifier = Modifier.weight(2f),
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Orange,
                                uncheckedThumbColor = Color.White,
                            )
                        )
                        if (switch) {
                            IconButton(
                                onClick = {
                                    openDialog = isEnabled
                                    //  TODO("Solo se puede cambiar si no firmo y fotos y entregables")
                                },
                                enabled = if (userHasSignature) false else isEnabled,
                                modifier = Modifier.weight(3f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Draw,
                                    contentDescription = null,
                                    tint = if (contentFirm) Verde else Orange
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.weight(3f))
                        }
                    }

                    if (openDialog) {
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
                                    ButtonCustom(
                                        value = "Cancelar",
                                        enabled = isEnabled,
                                        onClick = {
                                            controller.reset()
                                            openDialog = false
                                        })
                                    Spacer(modifier = Modifier.padding(10.dp))
                                    ButtonCustom(
                                        value = "Continuar",
                                        enabled = isEnabled,
                                        onClick = {
                                            contentFirm = true
                                            controller.getDrawBoxBitmap()?.let { bitmap ->
                                                val uri = context.saveImage(bitmap)
                                                imageFirm = uri
                                            }
                                            openDialog = false
                                        })
                                }
                            }
                        }
                    }

                    imageFirm?.let {
                        addToMap(
                            student.name,
                            it
                        )
                    }
                }

            }
            save = false
        }
    }
}

@Composable
fun AssistantTopList(save: () -> Unit) {
    Column {
        Box {
            Text(
                text = "Lista de asistencia",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = androidx.compose.ui.Modifier
                    .background(Orange)
                    .padding(vertical = 15.dp)
                    .fillMaxWidth()
            )
            IconButton(
                onClick = {
                    save()
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 25.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            Text(
                text = "Nombre y cedula",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .weight(9f)
            )
            Text(
                text = "Asistencia",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(2f)
            )
            Text(
                text = "Firma",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(3f)
            )
        }
    }
}

private fun Context.saveImage(bitmap: Bitmap): Uri? {
    var uri: Uri? = null
    try {
        val fileName = System.nanoTime().toString() + ".png"
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/")
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            } else {
                val directory =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                val file = File(directory, fileName)
                put(MediaStore.MediaColumns.DATA, file.absolutePath)
            }
        }

        uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        uri?.let {
            contentResolver.openOutputStream(it).use { output ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.apply {
                    clear()
                    put(MediaStore.Audio.Media.IS_PENDING, 0)
                }
                contentResolver.update(uri, values, null, null)
            }
        }
        return uri
    } catch (e: java.lang.Exception) {
        if (uri != null) {
            // Don't leave an orphan entry in the MediaStore
            contentResolver.delete(uri, null, null)
        }
        throw e
    }
}
