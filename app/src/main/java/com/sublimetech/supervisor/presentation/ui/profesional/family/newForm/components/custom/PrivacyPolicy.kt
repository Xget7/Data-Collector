package com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import com.sublimetech.supervisor.R
import com.sublimetech.supervisor.core.components.ButtonCustom
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.PageInfo
import com.sublimetech.supervisor.presentation.utils.Utils.saveImage
import io.ak1.drawbox.DrawBox
import io.ak1.drawbox.rememberDrawController

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PrivacyPolicy(
    pageInfo: PageInfo,
    studentName: String,
    studentDocument: String,
    familyName: String,
    familyDocument: String,
    signature: String,
    saveSignature: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White,
        elevation = 10.dp,
        modifier = Modifier
            .padding(50.dp)
            .fillMaxSize()
    ) {
        Box() {
            Column(
                modifier = Modifier
                    .padding(end = 50.dp, start = 50.dp, top = 60.dp, bottom = 90.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(
                        text = "AUTORIZACIÓN Y CONSENTIMIENTO INFORMADO\n" +
                                "USO DE DATOS PERSONALES E IMAGEN\n" +
                                "AÑO 2022",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(10.dp))


                    var firmDialog2 by remember { mutableStateOf(true) }
                    var firmDialog by remember { mutableStateOf(false) }
                    val context = LocalContext.current
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
                                            color = Color.Cyan
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

                                                saveSignature(context.saveImage(bitmap).toString())

                                            }
                                            firmDialog = false
                                        })
                                }
                            }
                        }
                    }

                    Text(
                        text = "Quien suscribe el presente documento, " +
                                "obrando en calidad de madre o padre de " +
                                "familia o cuidador del estudiante de nombre " +
                                "$studentName identificado con documento No: $studentDocument" +
                                ", y como representante de todos los miembros del respectivo núcleo familiar," +
                                " de conformidad con los dispuesto en las normas vigentes sobre protección de " +
                                "datos personales, en especial las leyes 1581 de 2012, 1712 de 2014 y los " +
                                "decreto 1074 de 2015 y 1081 de 2015, autorizado libre e inequívocamente a la " +
                                "FUNDACIÓN SUBLIME y a el Municipio de Soplaviento, departamento de Bolívar, para " +
                                "que en ejecución de los diferentes programas del proyecto Soplaviento municipio " +
                                "Constructor de Paz y Convivencia, ejecutado por contrato o convenio No: CONV-ASOC-002-2022 " +
                                "celebrado entre la fundación y el municipio, procedan al tratamiento de datos tales " +
                                "como fotografías, videos, audios, locaciones e información de los menores de edad y " +
                                "adultos que forman parte del núcleo familiar y/o intervienen como parte del mismo en " +
                                "las actividades de los programas del citado proyecto y convenio." +
                                "\n" +
                                "\n" +
                                "Entiendo que los responsables de los datos autorizados son el Municipio de Soplaviento, " +
                                "departamento de Bolívar y la FUNDACIÓN SUBLIME, identificada con NIT 901480793-9, y que " +
                                "la autorización comprende" +
                                "\n" +
                                "\n" +
                                "1. La recolección, gestión, almacenamiento y tratamiento a los datos personales relacionados en la autorización\n" +
                                "\n" +
                                "2. Mantener en sus archivos, usar, reproducir, publicar, adaptar, extraer o compendiar imágenes personales, fotográficas u otros datos autorizados; realizar videos y audios de los menores de edad y adultos involucrados en la ejecución del proyecto mencionado anteriormente, según corresponda.\n" +
                                "\n" +
                                "3. Divulgar y publicar las imágenes, audios u otros datos autorizados, a través de cualquier " +
                                "medio físico, electrónico, digital o de cualquier otra naturaleza, pública o privada, con el " +
                                "fin de hacer prevención y promoción de derechos de los niños, niñas, adolescentes, mujeres y " +
                                "familias y demás campañas institucionales y publicitarias propias del Municipio de Soplaviento, " +
                                "departamento de Bolívar y la FUNDACIÓN SUBLIME, sus actuales y futuros productos, servicios, " +
                                "marcas, programas y proyectos, garantizando que las actividades que se realizarán se encuentran " +
                                "enmarcadas en el interés superior de los menores de edad, y en el respeto de los derechos " +
                                "fundamentales de los titulares." +
                                "\n" +
                                "\n" +
                                "Manifiesto que como titular de la información y/o representante legal del titular, fui informado de " +
                                "los derechos con que cuento, especialmente a conocer, actualizar y rectificar mi información " +
                                "personal, revocar la autorización y solicitar la supresión de los datos autorizados. Reconozco" +
                                " además que no existe expectativa sobre los eventuales efectos económicos de la divulgación, o " +
                                "sobre el tipo de campaña publicitaria que pueda realizar el Municipio de Soplaviento " +
                                "departamento de Bolívar y la FUNDACIÓN SUBLIME." +
                                "\n" +
                                "\n" +
                                "Declaro que conozco que los propósitos del Municipio de Soplaviento departamento de Bolívar" +
                                " y la FUNDACIÓN SUBLIME apuntan a promocionar valores educativos, culturales y de divulgación " +
                                "de políticas públicas, hecho por el cual en las emisiones no habrá uso indebido del material " +
                                "autorizado, ni distinto al anteriormente descrito, y menos irrespeto por cualquier derecho" +
                                " fundamental." +
                                "\n" +
                                "\n" +
                                "Reconozco que la vigencia temporal y territorial de esta autorización está dada para las gestiones " +
                                "propias e institucionales de la Entidad en los términos establecido en las Leyes 1581 de 2012, " +
                                "1712 de 2014 y los Decretos 1074 de 2015 y 1081 de 2015, por lo que, además, el Municipio " +
                                "de Soplaviento departamento de Bolívar y la FUNDACIÓN SUBLIME son titulares de los derechos" +
                                " sobre los programas o productos a emitir correspondientemente." +
                                "\n" +
                                "\n" +
                                "Manifiesto que la presente autorización me fue solicitada y puesta de presente antes de " +
                                "entregar mis datos y que la suscribo de forma libre y voluntaria una vez leída en su totalidad" +
                                "\n" +
                                "\n" +
                                "Se firma en el municipio de Soplaviento departamento de Bolívar a los 5 días " +
                                "del mes de Diciembre del año 2022" +
                                "\n" +
                                "\n" +
                                "\n",
                        fontWeight = FontWeight.Medium,
                        fontSize = 17.sp
                    )
                    Card(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(10),
                        modifier = Modifier
                            .height(200.dp)
                            .width(400.dp)
                            .padding(bottom = 20.dp)

                    ) {
                        if (signature != "") {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    signature
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
                                painter = painterResource(id = R.drawable.icono_de_firma),
                                contentDescription = null
                            )
                        }

                    }
                    Text(
                        text = "Nombre: $familyName" +
                                "\n" +
                                "C.C. $familyDocument" +
                                "\n" +
                                "\n",
                        fontWeight = FontWeight.Medium,
                        fontSize = 17.sp
                    )
                }


            }
            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(30.dp)
            ) {
                FirstMomentTitle()
            }
            Text(
                text = "${pageInfo.page + 1}/${pageInfo.pagesNumber}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(30.dp)
            )
//            Card(
//                shape = CircleShape,
//                elevation = 1.dp,
//                modifier = Modifier
//                    .align(Alignment.BottomEnd)
//                    .padding(30.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.ArrowForward,
//                    contentDescription = null, tint = Orange,
//                    modifier = Modifier
//                        .clickable {
//                            pageInfo.changePage(pageInfo.page + 1)
//                            pageInfo.changeDirection(true)
//                        }
//                        .padding(10.dp)
//                        .size(40.dp)
//                )
//            }
//            Card(
//                shape = CircleShape,
//                elevation = 1.dp,
//                modifier = Modifier
//                    .align(Alignment.BottomStart)
//                    .padding(30.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.ArrowForward,
//                    contentDescription = null, tint = Orange,
//                    modifier = Modifier
//                        .clickable {
//                            pageInfo.changePage(pageInfo.page - 1)
//                            pageInfo.changeDirection(false)
//                        }
//                        .rotate(180f)
//                        .padding(10.dp)
//                        .size(40.dp)
//                )
//            }
        }
    }

}