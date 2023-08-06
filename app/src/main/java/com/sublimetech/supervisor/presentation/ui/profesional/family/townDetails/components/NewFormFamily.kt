package com.sublimetech.supervisor.presentation.ui.profesional.family.townDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.sublimetech.supervisor.R

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NewFormFamily(
    openForm: () -> Unit,
    openPdf: () -> Unit
) {
    Column {
        Card(
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 5.dp,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 0.dp,
                    bottom = 20.dp
                )
                .width(420.dp)
                .height(245.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        elevation = 5.dp,
                        modifier = Modifier
                            .size(170.dp)
                    ) {

                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    openForm()
                                }
                        ) {


                            Image(
                                painter = painterResource(id = R.drawable.abrirformato),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(vertical = 0.dp, horizontal = 0.dp),
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Abrir formato",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        elevation = 5.dp,
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .width(208.dp)
                            .height(50.dp)
                            .clickable {
                                openPdf()
                            }
                    ) {
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .clickable {
                                    openPdf()
                                }
                                .fillMaxSize()
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.pdfnaranja),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(39.dp)
                                    .clickable {
                                        openPdf()
                                    }
                            )
                            Text(
                                text = "Guia de apoyo",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 15.sp,
                                modifier = Modifier
                                    .padding(bottom = 5.dp)
                                    .clickable {
                                        openPdf()
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun Lol123() {
    NewFormFamily({}, {})
}