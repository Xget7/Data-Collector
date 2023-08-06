package com.sublimetech.supervisor.presentation.ui.profesional.woman.newForm.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Today
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sublimetech.supervisor.domain.model.WomanForm.ProjectBasicInfo
import com.sublimetech.supervisor.domain.model.youthAndWoman.Form
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
@Composable
fun DialogMoreInfo(
    openDialog: Boolean,
    animationDialog: Boolean,
    form: Form,
    project: ProjectBasicInfo,
    onDismissRequest: () -> Unit
) {

    if (openDialog) {
        Dialog(
            onDismissRequest = {
                onDismissRequest()
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
        ) {
            AnimatedVisibility(
                visible = animationDialog,
                enter = scaleIn(
                    animationSpec = tween(
                        durationMillis = 500
                    )
                ) + slideInVertically(
                    initialOffsetY = { it / 2 },
                    animationSpec = tween(
                        durationMillis = 1000
                    )
                )
            ) {
                Box() {
                    Box(modifier = Modifier
                        .clickable(MutableInteractionSource(), indication = null) {
                            onDismissRequest()
                        }
                        .fillMaxSize()) {}
                    Card(
                        shape = RoundedCornerShape(40.dp),
                        modifier = Modifier
                            .padding(50.dp)
                            .align(Alignment.TopCenter)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(50.dp)
                        ) {
                            Text(
                                text = "Informacion",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                modifier = Modifier.padding(20.dp)
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                Column() {
                                    TitleAndContent(
                                        title = "Proyecto",
                                        content = project.name,
                                    )
                                    TitleAndContent(
                                        title = "Convenio",
                                        content = project.agreement,//"CONV-ASOC-002-2022" ,
                                        icon = null,
                                        content2 = null,
                                        icon2 = null
                                    )
                                    TitleAndContent(
                                        title = "Lugar y fecha del convenio",
                                        content = project.agreementPlace,//"Bolívar, Soplaviento" ,
                                        icon = Icons.Outlined.Place,
                                        content2 = project.agreementDate,
                                        icon2 = Icons.Outlined.Today
                                    )

                                    TitleAndContent(
                                        title = "Objeto",
                                        content = project._object,//"Aunar esfuerzos para el fortalecimiento de la convivencia ciudadana a partir de la intervención integral en jovenes, mujeres y nucleos familiares, para generar una dinámica  social de amor por la vida, la paz y el emprendimiento dinamizando la economía popular del municipio de soplaviento" ,
                                        icon = null,
                                        content2 = null,
                                        icon2 = null
                                    )
                                }
                                Spacer(modifier = Modifier.width(50.dp))
                                Column() {

                                    TitleAndContent(
                                        title = "Grupo de trabajo",
                                        content = form.groupName,
                                        icon = null,
                                        content2 = null,
                                        icon2 = null
                                    )

                                    form.area?.let {
                                        TitleAndContent(
                                            title = "Area",
                                            content = it,
                                            icon = null,
                                            content2 = null,
                                            icon2 = null
                                        )
                                    }

                                    TitleAndContent(
                                        title = "Sesion #${form.sessionNumber}",
                                        content = "Taller teorico/practico",
                                        icon = null,
                                        content2 = null,
                                        icon2 = null
                                    )



                                    TitleAndContent(
                                        title = "Fecha y hora de inicio",
                                        content = form.startDate,
                                        icon = Icons.Outlined.Today,
                                        content2 = form.startTime,
                                        icon2 = Icons.Outlined.Schedule
                                    )

                                    TitleAndContent(
                                        title = "Tallerista",
                                        content = form.professionalName,
                                        icon = Icons.Outlined.Person,
                                        content2 = form.professionalDocumentId,
                                        icon2 = Icons.Outlined.Badge
                                    )
                                }
                            }
                        }

                    }
                }
            }

        }
    }
}


fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

@Composable
private fun TitleAndContent(
    title: String,
    content: String,
    icon: ImageVector? = null,
    content2: String? = null,
    icon2: ImageVector? = null
) {
    Spacer(modifier = Modifier.padding(15.dp))
    Column(
        modifier = Modifier
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colors.onBackground
        )

        Spacer(modifier = Modifier.padding(1.dp))

        Row {

            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.LightGray
                )
                Spacer(modifier = Modifier.padding(2.dp))
            }
            Text(
                text = content,
                fontWeight = FontWeight.Bold,
                color = Color.LightGray,
                fontSize = 17.sp,
                textAlign = TextAlign.Justify,
                modifier = Modifier.widthIn(max = 300.dp)
            )
        }

        if (content2 != null) {
            Row {
                if (icon2 != null) {
                    Icon(
                        imageVector = icon2,
                        contentDescription = null,
                        tint = Color.LightGray
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                }
                Text(
                    text = content2,
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray,
                    fontSize = 17.sp
                )
            }
        }
    }
}