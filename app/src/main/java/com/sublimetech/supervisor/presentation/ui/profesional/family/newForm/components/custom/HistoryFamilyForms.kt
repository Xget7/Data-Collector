package com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.custom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.sublimetech.supervisor.data.model.family.FamilyDto
import com.sublimetech.supervisor.ui.theme.Blue
import com.sublimetech.supervisor.ui.theme.Orange
import com.sublimetech.supervisor.ui.theme.Purple
import com.sublimetech.supervisor.ui.theme.White


@Composable
fun HistoryFamilyForm(
    formList: List<FamilyDto>,
    onFormClicked: (FamilyDto) -> Unit
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
            formList.sortedBy { it.createdTime }.forEach {

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
                                onFormClicked(it)
                            }
                            .padding(20.dp)
                    ) {
                        it.name.let { name ->
                            Text(
                                text = "Familia de $name",
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
                                horizontalArrangement = Arrangement.spacedBy(13.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                for (i in 1..3) {
                                    MomentItem(i, it.visit.currentPhase!! >= i)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MomentItem(moment: Int, enabled: Boolean) {
    Card(
        backgroundColor = if (enabled) {
            when (moment) {
                1 -> Orange
                2 -> Purple
                3 -> Blue
                else -> {
                    Color.LightGray
                }
            }
        } else Color.LightGray,
        elevation = 10.dp,
        shape = RoundedCornerShape(40),
        modifier = Modifier.width(120.dp)
    ) {
        Text(
            text = "Momento #$moment",
            fontWeight = FontWeight.Bold,
            color = White,
            fontSize = 17.sp,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
        )
    }
}