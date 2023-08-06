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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sublimetech.supervisor.data.model.family.FamilyRelationship
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.PageInfo

@Composable
fun AdditionalInfoComponent(
    pageInfo: PageInfo,
    familyRelationship: MutableState<FamilyRelationship>,
    saveAditionalInfo: (FamilyRelationship) -> Unit
) {


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
                    .padding(horizontal = 50.dp, vertical = 100.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                familyRelationship.value.apply {
                    MultipleOptionsConservingAsk(
                        label = "¿Cenan juntos?",
                        returnSelectedItem = {
                            familyRelationship.value = copy(haveDinnerTogether = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf("Nunca", "Aveces", "Siempre"),
                        selected = haveDinnerTogether
                    )
                    MultipleOptionsConservingAsk(
                        label = "¿Le dices a tu familia lo mucho que la quieres?",
                        returnSelectedItem = {
                            familyRelationship.value = copy(showAffection = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf("Nunca", "Aveces", "Siempre"),
                        selected = showAffection
                    )
                    MultipleOptionsConservingAsk(
                        label = "¿Tratas a tu familia con afecto y dedicación?",
                        returnSelectedItem = {
                            familyRelationship.value = copy(showDedication = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf("Si", "No"),
                        selected = showDedication
                    )

                    MultipleOptionsConservingAsk(
                        label = "¿Le haces saber a tu familia lo importante que es para ti?",
                        returnSelectedItem = {
                            familyRelationship.value = copy(communicateImportance = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf("Nunca", "Aveces", "Siempre"),
                        selected = communicateImportance
                    )
                    CustomTextField(
                        onValueChange = { familyRelationship.value = copy(whatYouLike = it) },
                        label = "¿Qué es lo que más te gusta de tu familia?",
                        value = whatYouLike
                    )
                    CustomTextField(
                        onValueChange = {
                            familyRelationship.value = copy(whatYouWouldChange = it)
                        },
                        label = "Si pudieras cambiar algo en tu familia, ¿qué sería?",
                        value = whatYouWouldChange
                    )
                    CustomTextField(
                        onValueChange = { familyRelationship.value = copy(happiestMoment = it) },
                        label = "¿Cuál es el momento más feliz que recuerdas en familia?",
                        value = happiestMoment
                    )
                    CustomTextField(
                        onValueChange = { familyRelationship.value = copy(mostLikedQuality = it) },
                        label = "¿Cuál es la cualidad que más te gusta de tu familia?",
                        value = mostLikedQuality
                    )
                    MultipleOptionsConservingAsk(
                        label = "¿Se respetan las ideas e identidades de cada miembro de la familia?",
                        returnSelectedItem = {
                            familyRelationship.value = copy(respectIdentities = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf("Nunca", "Aveces", "Siempre"),
                        selected = respectIdentities
                    )
                    MultipleOptionsConservingAsk(
                        label = "¿Qué tan feliz sientes que es tu familia?",
                        returnSelectedItem = {
                            familyRelationship.value = copy(isYourFamilyHappy = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf("Nada", "Poco", "Regular", "Mucho", "NSNR"),
                        selected = isYourFamilyHappy
                    )
                    MultipleOptionsConservingAsk(
                        label = "¿Sientes orgullo por tu familia?",
                        returnSelectedItem = { familyRelationship.value = copy(feelProud = it) },
                        enabled = true,
                        listaDeInstituciones = listOf("Si", "No"),
                        selected = feelProud
                    )

                    MultipleOptionsConservingAsk(
                        label = "¿Cómo resuelven las dificultades en familia?",
                        returnSelectedItem = {
                            familyRelationship.value = copy(howToResolveDifficulties = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf(
                            "Dialogo",
                            "Imposicion",
                            "Lo que diga el jefe de hogar",
                            "Ninguna"
                        ),
                        selected = howToResolveDifficulties
                    )
                    MultipleOptionsConservingAsk(
                        label = "¿En cuál de estos temas le gustaría recibir esa orientación?",
                        returnSelectedItem = {
                            familyRelationship.value = copy(topicsForOrientation = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf(
                            "Comunicación en familia",
                            "Buena crianza",
                            "Hábitos saludables",
                            "Bullying",
                            "Educación sexual",
                            "Solución pacífica de conflictos",
                            "Buen trato",
                            "Prevención de consumo de PSA",
                            "Ley de infancia y adolescencia",
                            "Ninguno"
                        ),
                        selected = topicsForOrientation
                    )
                    MultipleOptionsConservingAsk(
                        label = "¿Cómo describes la relación de tu familia?",
                        returnSelectedItem = {
                            familyRelationship.value = copy(describeFamilyRelationship = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf(
                            "Muy buena",
                            "Buena",
                            "Regular",
                            "Mala",
                            "Normal"
                        ),
                        selected = describeFamilyRelationship
                    )
                    CustomTextField(
                        onValueChange = {
                            familyRelationship.value =
                                familyRelationship.value.copy(professionalObservations = it)
                        },
                        label = "Observaciones del profesional",
                        value = familyRelationship.value.professionalObservations
                    )
                }


            }

            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(30.dp),
                verticalAlignment = Alignment.CenterVertically
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

        }
    }
}
