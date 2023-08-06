package com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.forms

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import com.sublimetech.supervisor.core.components.ButtonCustom
import com.sublimetech.supervisor.data.model.family.VisitDto
import com.sublimetech.supervisor.data.model.youtAndWoman.StudentDto
import com.sublimetech.supervisor.domain.model.DevelopmentPhoto
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.CustomTextField
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.custom.EvidenceDevelopmentOfThematics
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.custom.PlayfulEvidence
import com.sublimetech.supervisor.presentation.ui.profesional.woman.newForm.components.AttendanceTaking
import com.sublimetech.supervisor.presentation.utils.Utils.saveImage
import com.sublimetech.supervisor.ui.theme.Orange
import io.ak1.drawbox.DrawBox
import io.ak1.drawbox.rememberDrawController


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SecondStage(
    onClick: () -> Unit,
    assistanceHashMap: HashMap<String, String>,
    visit: MutableState<VisitDto>,
    students: List<StudentDto>,
    removeFromMap: (String) -> Unit,
    addToMap: (String, String) -> Unit,
    scrollState: ScrollState,
    updateSignature: (String) -> Unit,
) {


    var localSignature = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current


    val funComment1 = remember {
        mutableStateOf(visit.value.funDevelopmentPhotos["1"]?.comment.orEmpty().orEmpty())
    }
    val funComment2 = remember {
        mutableStateOf(visit.value.funDevelopmentPhotos["2"]?.comment.orEmpty())
    }
    val funComment3 = remember {
        mutableStateOf(visit.value.funDevelopmentPhotos["3"]?.comment.orEmpty())
    }

    val tematicComment1 = remember {
        mutableStateOf(visit.value.thematicDevelopmentPhotos["3"]?.comment.orEmpty())
    }
    val tematicComment2 = remember {
        mutableStateOf(visit.value.thematicDevelopmentPhotos["3"]?.comment.orEmpty())
    }
    val tematicComment3 = remember {
        mutableStateOf(visit.value.thematicDevelopmentPhotos["3"]?.comment.orEmpty())
    }


    fun update() {
        for (i in 1..3) {
            visit.value.funDevelopmentPhotos[i.toString()].let {
                if (it == null) {
                    visit.value.funDevelopmentPhotos[i.toString()] =
                        DevelopmentPhoto(
                            comment = when (i) {
                                1 -> funComment1.value
                                2 -> funComment2.value
                                3 -> funComment3.value
                                else -> {
                                    funComment1.value
                                }
                            }
                        )
                } else {
                    visit.value.funDevelopmentPhotos[i.toString()]?.comment =
                        when (i) {
                            1 -> funComment1.value
                            2 -> funComment2.value
                            3 -> funComment3.value
                            else -> {
                                funComment1.value
                            }
                        }
                }
            }
            visit.value.thematicDevelopmentPhotos[i.toString()].let {
                if (it == null) {
                    visit.value.thematicDevelopmentPhotos[i.toString()] =
                        DevelopmentPhoto(
                            comment = when (i) {
                                1 -> tematicComment1.value
                                2 -> tematicComment2.value
                                3 -> tematicComment3.value
                                else -> {
                                    tematicComment1.value
                                }
                            }
                        )
                } else {
                    visit.value.thematicDevelopmentPhotos[i.toString()]?.comment =
                        when (i) {
                            1 -> tematicComment1.value
                            2 -> tematicComment2.value
                            3 -> tematicComment3.value
                            else -> {
                                tematicComment1.value
                            }
                        }
                }
            }
        }
    }




    Card(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White,
        elevation = 10.dp,
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)
    ) {
        Box() {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(end = 50.dp, start = 50.dp)
            ) {
                key(visit.value.funDevelopmentPhotos) {
                    PlayfulEvidence(visit.value.funDevelopmentPhotos)
                }
                CustomTextField(
                    onValueChange = {
                        funComment1.value = it
                        update()
                    },
                    label = "Ludica #1",
                    value = funComment1.value
                )


                CustomTextField(
                    onValueChange = {
                        funComment2.value = it
                        update()
                    },
                    label = "Ludica #2",
                    value = funComment2.value
                )

                CustomTextField(
                    onValueChange = {
                        funComment3.value = it
                        update()
                    },
                    label = "Ludica #3",
                    value = funComment3.value.orEmpty()
                )


                EvidenceDevelopmentOfThematics(
                    visit.value.thematicDevelopmentPhotos
                )

                CustomTextField(
                    onValueChange = {
                        tematicComment1.value = it
                        update()
                    },
                    label = "Desarrollo Temático #1",
                    value = tematicComment1.value.orEmpty()
                )

                CustomTextField(
                    onValueChange = {
                        tematicComment2.value = it
                        update()
                    },
                    label = "Desarrollo Temático #2",
                    value = tematicComment2.value.orEmpty()
                )

                CustomTextField(
                    onValueChange = {
                        tematicComment3.value = it
                        update()
                    },
                    label = "Desarrollo Temático #3",
                    value = tematicComment3.value.orEmpty()
                )


                Spacer(modifier = Modifier.padding(20.dp))
                // Aquí va el codigo de toma de asistencia
                AttendanceTaking(
                    students = students,
                    removeFromMap = {
                        removeFromMap(it)
                    },
                    addToMap = { key, uri ->
                        addToMap(key, uri.toString())
                    },
                    attendancesList = assistanceHashMap,
                    isEnabled = true,
                    horizontalPadding = 30.dp
                )
                Spacer(modifier = Modifier.padding(20.dp))


                var firmDialog2 by remember { mutableStateOf(true) }
                var firmDialog by remember { mutableStateOf(false) }

                val controller = rememberDrawController()
                controller.setStrokeColor(Color.Black)
                controller.setStrokeWidth(1.5f)

                ////////////////////////////////////////////////////////////////////////////////////
                // Ignore !

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

                // Ignorar ¡
                ////////////////////////////////////////////////////////////////////////////////////


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
                                ButtonCustom(
                                    value = "Cancelar",
                                    enabled = true,
                                    onClick = {
                                        controller.reset()
                                        firmDialog = false
                                    })
                                Spacer(modifier = Modifier.padding(10.dp))
                                ButtonCustom(
                                    value = "Continuar",
                                    enabled = true,
                                    onClick = {
                                        controller.getDrawBoxBitmap()?.let { bitmap ->
                                            val uri = context.saveImage(bitmap)
                                            localSignature.value = uri.toString()

                                            updateSignature(localSignature.value)
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
                    if (localSignature.value != "") {
                        Image(
                            painter = rememberAsyncImagePainter(
                                localSignature.value
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    controller.reset()
                                    firmDialog = true
                                }
                        )
                    } else {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    controller.reset()
                                    firmDialog = true
                                }
                        ) {

                        }
                        Image(
                            painter = painterResource(id = com.sublimetech.supervisor.R.drawable.icono_de_firma),
                            contentDescription = null
                        )
                    }
                }
                ButtonCustom(
                    value = "Guardar formato",
                    onClick = {

                        Log.d(
                            "filteredThematicAndFun THEMATC",
                            visit.value.thematicDevelopmentPhotos.toString()
                        )
                        Log.d(
                            "filteredThematicAndFun FUN ",
                            visit.value.funDevelopmentPhotos.toString()
                        )
                        onClick()
                    },
                    enabled = true
                )
                Spacer(modifier = Modifier.padding(15.dp))
            }

        }
    }


}

