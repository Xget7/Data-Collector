package com.sublimetech.supervisor.presentation.ui.profesional.woman.townDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.BusinessCenter
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Lan
import androidx.compose.material.icons.rounded.Workspaces
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.sublimetech.supervisor.ui.theme.Orange

@Composable
fun TownHeader(
    nombreDelMunicipio: String,
    beneficiarios: Int,
    colaboradores: Int,
    talleres: Int,
    image: String,
    onBack: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(bottomEnd = 50.dp, bottomStart = 50.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 0.dp)
        ) {
            Column() {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onBackground,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(0.dp))
                    Text(
                        text = nombreDelMunicipio,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        fontSize = 40.sp,
                        color = MaterialTheme.colors.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.padding(start = 30.dp)) {
                    Column() {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Icon(
                                imageVector = Icons.Rounded.Groups,
                                contentDescription = null,
                                tint = Orange
                            )

                            Text(
                                text = "$beneficiarios Beneficiarios",
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                fontSize = 15.sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(start = 5.dp, top = 10.dp)
                            )
                        }
                        Row(verticalAlignment = Alignment.Bottom) {
                            Icon(
                                imageVector = Icons.Rounded.Lan,
                                contentDescription = null,
                                tint = Orange
                            )

                            Text(
                                text = "5 Programas",
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                fontSize = 15.sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(start = 5.dp, top = 10.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    Column() {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Icon(
                                imageVector = Icons.Rounded.BusinessCenter,
                                contentDescription = null,
                                tint = Orange
                            )

                            Text(
                                text = "$colaboradores Colaboradores",
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                fontSize = 15.sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(start = 5.dp, top = 10.dp)
                            )
                        }
                        Row(verticalAlignment = Alignment.Bottom) {
                            Icon(
                                imageVector = Icons.Rounded.Workspaces,
                                contentDescription = null,
                                tint = Orange
                            )

                            Text(
                                text = "$talleres Talleres / Momentos",
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                fontSize = 15.sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(start = 5.dp, top = 10.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }

            Image(
                painter = rememberAsyncImagePainter(model = image),
                contentDescription = "",
                modifier = Modifier
                    .padding(vertical = 0.dp, horizontal = 0.dp)
                    .width(120.dp)
                    .height(120.dp)
            )
        }
    }
}