package com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sublimetech.supervisor.core.components.ButtonCustom
import com.sublimetech.supervisor.data.model.family.ProgramFeedback
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.CustomTextField
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.MultipleOptionsConservingAsk
import com.sublimetech.supervisor.ui.theme.Orange

@Composable
fun ThirdStage(
    programFeedback: MutableState<ProgramFeedback>,
    onClick: () -> Unit
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
                    .padding(50.dp)
                    .padding(top = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                programFeedback.value.apply {
                    MultipleOptionsConservingAsk(
                        label = "¿Como le pareció el programa?",
                        returnSelectedItem = {
                            programFeedback.value = copy(programOpinion = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf("Nunca", "Aveces", "Siempre"),
                        selected = programOpinion
                    )
                    CustomTextField(
                        onValueChange = {
                            programFeedback.value = copy(favoriteProgramAspect = it)
                        },
                        label = "¿Que fue lo que mas te gusto del programa?",
                        value = favoriteProgramAspect
                    )

                    CustomTextField(
                        onValueChange = {
                            programFeedback.value = copy(programLessonsLearned = it)
                        },
                        label = "¿Qué enseñanzas le dejo el programa?",
                        value = programLessonsLearned
                    )

                    MultipleOptionsConservingAsk(
                        label = "¿Le gustaría repetir este programa el año que viene?",
                        returnSelectedItem = {
                            programFeedback.value = copy(programParticipationDesire = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf("Nunca", "Aveces", "Siempre"),
                        selected = programParticipationDesire
                    )
                    CustomTextField(
                        onValueChange = {
                            programFeedback.value = copy(programNewFeatureRequests = it)
                        },
                        label = "¿Qué cosas nuevas le gustaría que tuviera el programa?",
                        value = programNewFeatureRequests
                    )
                    CustomTextField(
                        onValueChange = {
                            programFeedback.value = copy(programImprovementSuggestions = it)
                        },
                        label = "¿Qué cosas le gustaría que se mejoren en el programa?,?",
                        value = programImprovementSuggestions
                    )
                    CustomTextField(
                        onValueChange = {
                            programFeedback.value = copy(anticipatedFamilyChanges = it)
                        },
                        label = "¿Qué cosas piensa que pueden cambiar su familia despues del programa?",
                        value = anticipatedFamilyChanges
                    )
                    CustomTextField(
                        onValueChange = { programFeedback.value = copy(messageToMayor = it) },
                        label = "¿Qué mensaje quiere enviarle a el alcalde sobre este programa?",
                        value = messageToMayor
                    )
                    MultipleOptionsConservingAsk(
                        label = "¿Qué tanto cumplió sus expectativas del desarrollo del programa?",
                        returnSelectedItem = {
                            programFeedback.value = copy(programExpectationFulfillment = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf("Nunca", "Aveces", "Siempre"),
                        selected = programExpectationFulfillment
                    )
                    MultipleOptionsConservingAsk(
                        label = "Señale un problema que considere frecuente en su vecindad",
                        returnSelectedItem = {
                            programFeedback.value = copy(neighborhoodCommonProblem = it)
                        },
                        enabled = true,
                        listaDeInstituciones = listOf("Nada", "Poco", "Regular", "Mucho", "NSNR"),
                        selected = neighborhoodCommonProblem
                    )
                    CustomTextField(
                        onValueChange = {
                            programFeedback.value = copy(familyLifeImprovementAspect = it)
                        },
                        label = "¿Qué aspecto en particular  le gustaría que se mejore en su vida en familia?",
                        value = familyLifeImprovementAspect
                    )
                    CustomTextField(
                        onValueChange = {
                            programFeedback.value = copy(familyWishForImprovement = it)
                        },
                        label = "¿Si pudiera pedir un deseo de algo para mejorar la relacion en su familia, qué pediría?",
                        value = familyWishForImprovement
                    )

                }


                Row(
                    modifier = Modifier
                        .padding(30.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    ButtonCustom(
                        value = "Guardar formato",
                        onClick = onClick,
                        enabled = true
                    )
                }

            }

            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ThirdMomentTitle()
            }
        }
    }
}

@Composable
fun ThirdMomentTitle() {
    Icon(
        imageVector = Icons.Default.Circle,
        contentDescription = null,
        tint = Orange
    )
    Spacer(modifier = Modifier.width(22.dp))
    Text(
        text = "Tercer Momento",
        fontWeight = FontWeight.Bold,
        fontSize = 27.sp
    )
}