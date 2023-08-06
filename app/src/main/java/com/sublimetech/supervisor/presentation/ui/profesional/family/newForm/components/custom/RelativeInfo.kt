package com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sublimetech.supervisor.data.model.family.FamilyMemberDto
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.PageInfo
import java.util.UUID

@Composable
fun RelativeInfo(
    pageInfo: PageInfo,
    infoFamiliar: MutableState<FamilyMemberDto>,
    familyId: String,
    index: Int
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

                infoFamiliar.value.apply {
                    OptionAndText(
                        onValueChange = {
                            infoFamiliar.value = copy(name = it)
                        },
                        label = "Nombre completo",
                        onDone = null,
                        labelMultipleOption = "   Genero   ",
                        returnSelectedItem = {
                            infoFamiliar.value = copy(gender = it)
                        },
                        options = listOf("Masculino", "Femenino"),
                        value = name,
                        selectedItem = gender,
                    )

                    IdentificationForm(
                        onValueChange = {
                            infoFamiliar.value = copy(documentNumber = it)
                        },
                        label = "Numero de documento",
                        onDone = null,
                        labelMultipleOption = "Tipo de doc.",
                        returnSelectedItem = {
                            infoFamiliar.value = copy(documentType = it)
                        },
                        options = listOf("T.I.", "C.C.", "C.E."),
                        value = documentNumber,
                        selectedItem = documentType,
                    )

                    DateSelector(
                        context = LocalContext.current,
                        fechaSeleccionada = {
                            infoFamiliar.value = copy(dateOfBirth = it)
                        },
                        fecha = dateOfBirth,
                    )

                    PhoneNumber(
                        onValueChange = {
                            infoFamiliar.value = copy(phoneNumber = it)
                        },
                        label = "Numero telefonico",
                        onDone = null,
                        value = phoneNumber,
                    )


                    CustomTextField(
                        onValueChange = {
                            infoFamiliar.value = copy(emailAddress = it)
                        },
                        label = "Correo electronico",
                        value = emailAddress,
                    )

                    MultipleOptionsConservingAsk(
                        label = "Vinculo con el estudiante",
                        returnSelectedItem = {
                            if (it != "Otro") {
                                infoFamiliar.value = copy(relationshipWithStudent = it)
                            }
                        },
                        enabled = true,
                        listaDeInstituciones = listOf(
                            "Padre",
                            "Madre",
                            "Abuelo(a)",
                            "Tío(a)",
                            "Primo(a)",
                            "Hermano(a)",
                            "Padrastro",
                            "Madrastra",
                            "Cuñado(a)",
                            "Esposo(a)",
                            "Otro"
                        ),
                        selected = relationshipWithStudent,
                    )


                    if (relationshipWithStudent == "Otro") {
                        CustomTextField(
                            onValueChange = {
                                infoFamiliar.value = copy(relationshipWithStudent = it)
                            },
                            label = "Tipo de vinculo con el estudiante",
                            value = relationshipWithStudent,
                        )
                    }

                    MultipleOptionsConservingAsk(
                        label = "Ocupacion",
                        returnSelectedItem = {
                            infoFamiliar.value = copy(occupation = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf(
                            "Independiente",
                            "Empleado",
                            "Informal",
                            "Hogar",
                            "Campo",
                            "Desempleado",
                            "Estudiante"
                        ),
                        selected = occupation,
                    )

                    MultipleOptionsConservingAsk(
                        label = "Condicion especial",
                        returnSelectedItem = {
                            infoFamiliar.value = copy(specialCondition = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf(
                            "Jefe de hogar",
                            "Adulto mayor",
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
                            infoFamiliar.value = copy(ethnicity = it)
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
                        selected = ethnicity,
                    )

                    MultipleOptionsConservingAsk(
                        label = "Grado de escolaridad",
                        returnSelectedItem = {
                            infoFamiliar.value = copy(educationLevel = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf(
                            "Primaria",
                            "Bachillerato",
                            "Tecnico",
                            "Tecnologo",
                            "Pregrado",
                            "Postgrado",
                            "Sin escolaridad"
                        ),
                        selected = educationLevel,
                    )

                    MultipleOptionsConservingAsk(
                        label = "Orientacion sexual",
                        returnSelectedItem = {
                            infoFamiliar.value = copy(sexualOrientation = it)
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
                        label = "Indicios de consumo de SPA",
                        returnSelectedItem = {
                            infoFamiliar.value = copy(PSAConsumption = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf(
                            "Alcohol",
                            "Drogas",
                            "Ninguno"
                        ),
                        selected = PSAConsumption,
                    )

                    id = UUID.randomUUID().toString()
                    this.familyId = familyId

                }

            }

            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(30.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                FirstMomentTitle("Informacion del familiar #${index + 1}:")
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
//                            saveInfoFamiliar(
//                                infoFamiliar.value
//                            )
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
//                            saveInfoFamiliar(
//                                infoFamiliar.value
//                            )
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