package com.sublimetech.supervisor.presentation.ui.profesional.woman.townDetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sublimetech.supervisor.domain.model.youthAndWoman.Form
import com.sublimetech.supervisor.ui.theme.Blue
import com.sublimetech.supervisor.ui.theme.Orange
import com.sublimetech.supervisor.ui.theme.Purple
import com.sublimetech.supervisor.ui.theme.Verde


@Composable
fun HistoryForm(
    page: Int,
    formList: List<Form>,
    onFormClicked: (String, Boolean) -> Unit
) {

    Card(
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(30.dp),
        elevation = 15.dp,
        modifier = Modifier
            .padding(horizontal = 50.dp)
            .height(800.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 50.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Historial de formatos",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            )
            formList.sortedBy { it.sessionNumber }.filter {
                it.groupName == when (page) {
                    (0) -> "Grupo A"
                    (1) -> "Grupo B"
                    (2) -> "Grupo C"
                    (3) -> "Grupo D"
                    else -> "Grupo A"
                }
            }.forEach {
                val color =
                    when (it.groupName) {
                        ("Grupo A") -> Orange
                        ("Grupo B") -> Purple
                        ("Grupo C") -> Blue
                        ("Grupo D") -> Verde
                        else -> {
                            Purple
                        }
                    }
                Card(
                    backgroundColor = Color.White,
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20),
                    modifier = Modifier
                        .padding(20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .clickable {
                                onFormClicked(it.id, it.isLocal)
                            }
                            .padding(20.dp)
                    ) {
                        it.area?.let { area ->
                            Text(
                                text = area,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                color = MaterialTheme.colors.onBackground
                            )

                        }

                        Spacer(modifier = Modifier.padding(5.dp))


                        Spacer(modifier = Modifier.padding(10.dp))
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Card(
                                    backgroundColor = color,
                                    elevation = 10.dp,
                                    shape = RoundedCornerShape(40)
                                ) {
                                    Text(
                                        text = "Sesion #${it.sessionNumber}",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colors.surface,
                                        fontSize = 20.sp,
                                        modifier = Modifier
                                            .padding(10.dp)
                                    )
                                }
                                Card(
                                    backgroundColor = color,
                                    elevation = 10.dp,
                                    shape = RoundedCornerShape(40),
                                    modifier = Modifier
                                        .padding(horizontal = 20.dp)
                                ) {
                                    Text(
                                        text = it.groupName,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colors.surface,
                                        fontSize = 20.sp,
                                        modifier = Modifier
                                            .padding(10.dp)
                                    )
                                }
                            }
                            Text(
                                text = it.startDate,
                                textAlign = TextAlign.End,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(horizontal = 20.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}
