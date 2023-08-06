package com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sublimetech.supervisor.data.model.family.FamilyStudentDto
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.PageInfo
import com.sublimetech.supervisor.ui.theme.Orange

@Composable
fun StudentInfoComponent(
    pageInfo: PageInfo,
    relativesQuantity: (Int) -> Unit,
    studentInfo: MutableState<FamilyStudentDto>,
    saveInfoEstudiante: (FamilyStudentDto) -> Unit
) {

    val verticalSCroll = rememberScrollState()

    Card(
        shape = RoundedCornerShape(50.dp),
        elevation = 10.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(50.dp)
            .fillMaxSize()
    ) {

        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(verticalSCroll)
                    .padding(horizontal = 50.dp, vertical = 100.dp)
            ) {
                studentInfo.value.apply {
                    CustomTextField(
                        value = name,
                        onValueChange = { studentInfo.value = copy(name = it) },
                        label = "Nombre completo"
                    )
                    IdentificationForm(
                        onValueChange = {
                            studentInfo.value = copy(docNumber = it)
                        },
                        label = "Numero de documento",
                        onDone = null,
                        labelMultipleOption = "Tipo de doc.",
                        returnSelectedItem = {
                            studentInfo.value = copy(docType = it)
                        },
                        options = listOf("T.I.", "C.C.", "C.E."),
                        value = docNumber,
                        selectedItem = docType,
                    )
                    DateSelector(
                        context = LocalContext.current,
                        fechaSeleccionada = {
                            studentInfo.value = copy(birthDate = it)
                        },
                        fecha = birthDate
                    )

                    MultipleOptionsConservingAsk(
                        label = "Institucion educativa",
                        returnSelectedItem = {
                            studentInfo.value = copy(educationalInstitution = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf(
                            "Simon Almanza Julio",
                            "Tecnica agropsicola liceo del dique Enrique Castillo jimenez"
                        ),
                        selected = educationalInstitution
                    )

                    OptionAndText(
                        onValueChange = {
                            studentInfo.value = copy(course = it)
                        },
                        label = "Curso",
                        onDone = null,
                        labelMultipleOption = "Grado",
                        returnSelectedItem = {
                            studentInfo.value = copy(grade = it)
                        },
                        options = listOf(
                            "0°",
                            "1°",
                            "2°",
                            "3°",
                            "4°",
                            "5°",
                            "6°",
                            "7°",
                            "8°",
                            "9°",
                            "10°",
                            "11°"
                        ),
                        value = course,
                        selectedItem = grade
                    )



                    MultipleOptionsConservingAsk(
                        label = "Jornada",
                        returnSelectedItem = {
                            studentInfo.value = copy(shift = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf(
                            "Mañana",
                            "Tarde",
                            "Extendida"
                        ),
                        selected = shift
                    )

                    MultipleOptionsConservingAsk(
                        label = "Condicion especial",
                        returnSelectedItem = {
                            studentInfo.value = copy(specialCondition = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf(
                            "Discapacidad fisica",
                            "Discapacidad sensorial",
                            "Discapacidad cognitiva",
                            "Victima",
                            "Desplazado",
                            "Ninguna"
                        ),
                        selected = specialCondition,
                    )

                    MultipleOptionsConservingAsk(
                        label = "Raza o etnia",
                        returnSelectedItem = {
                            studentInfo.value = copy(raceOrEthnicity = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf(
                            "Afro",
                            "ROM",
                            "Palenquero",
                            "Raizal",
                            "Indigena",
                            "Ninguna"
                        ),
                        selected = raceOrEthnicity,
                    )

                    MultipleOptionsConservingAsk(
                        label = "Orientacion sexual",
                        returnSelectedItem = {
                            studentInfo.value = copy(sexualOrientation = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf(
                            "Heterosexual",
                            "LGTBIQ",
                            "Prefiere no decir"
                        ),
                        selected = sexualOrientation,
                    )

                    MultipleOptionsConservingAsk(
                        label = "Manifestacion de maltrato",
                        returnSelectedItem = {
                            studentInfo.value = copy(signsOfMistreatment = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf(
                            "Verbal",
                            "Fisico",
                            "Psicologico",
                            "Ninguno"
                        ),
                        selected = signsOfMistreatment,
                    )

                    MultipleOptionsConservingAsk(
                        label = "Indicios de violencia",
                        returnSelectedItem = {
                            studentInfo.value = copy(signsOfViolence = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf(
                            "Intrafamiliar",
                            "Sexual",
                            "Interpersonal",
                            "Ninguno"
                        ),
                        selected = signsOfViolence,
                    )

                    MultipleOptionsConservingAsk(
                        label = "Familiares convivientes",
                        returnSelectedItem = {
                            studentInfo.value = copy(cohabitatingFamilyMembers = it)
                            relativesQuantity(it.toInt())
                        },
                        enabled = true,
                        listaDeInstituciones = listOf(
                            "1",
                            "2",
                            "3",
                            "4"
                        ),
                        selected = cohabitatingFamilyMembers,
                    )

                }

            }

            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(30.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                FirstMomentTitle("Informacion del estudiante:")
            }
            Text(
                text = "${pageInfo.page + 1}/${pageInfo.pagesNumber}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(30.dp)
            )


        }
    }
}

@Composable
fun FirstMomentTitle(title: String = "Primer Momento") {
    Icon(
        imageVector = Icons.Default.Circle,
        contentDescription = null,
        tint = Orange
    )
    Spacer(modifier = Modifier.width(22.dp))
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 27.sp
    )
}