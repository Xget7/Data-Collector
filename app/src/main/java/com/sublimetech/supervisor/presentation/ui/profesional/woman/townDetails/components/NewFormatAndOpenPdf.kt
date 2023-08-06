package com.sublimetech.supervisor.presentation.ui.profesional.woman.townDetails.components

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
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RadioButtonChecked
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.sublimetech.supervisor.R
import com.sublimetech.supervisor.ui.theme.Blue
import com.sublimetech.supervisor.ui.theme.Orange
import com.sublimetech.supervisor.ui.theme.Purple
import com.sublimetech.supervisor.ui.theme.Verde
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NewFormAndOpenPdf(
    pagerState: PagerState,
    openForm: (Int) -> Unit,
    openPdf: () -> Unit
) {
    Column {
        HorizontalPager(
            count = 4,
            state = pagerState
        ) { page ->

            Card(
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 5.dp,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset =
                            calculateCurrentOffsetForPage(page).absoluteValue

                        // We animate the scaleX + scaleY, between 85% and 100%
                        lerp(
                            start = 0.95f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(
                                0f,
                                1f
                            )
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(
                                0f,
                                1f
                            )
                        )
                    }
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.RadioButtonChecked,
                                contentDescription = null,
                                tint = when (page) {
                                    (0) -> Orange
                                    (1) -> Purple
                                    (2) -> Blue
                                    (3) -> Verde
                                    else -> Color.Gray
                                },
                                modifier = Modifier
                                    .size(30.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = when (page) {
                                    (0) -> "Grupo A"
                                    (1) -> "Grupo B"
                                    (2) -> "Grupo C"
                                    (3) -> "Grupo D"
                                    else -> "Grupo A"
                                },
                                fontWeight = FontWeight.Bold
                            )
                        }
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
                                        openForm(page)
                                    }
                            ) {
                                val imagenAbrirFormato = when (page) {
                                    (0) -> R.drawable.abrirformato
                                    (1) -> R.drawable.abrirformato2
                                    (2) -> R.drawable.abrirformato3
                                    (3) -> R.drawable.abrirformato4
                                    else -> R.drawable.abrirformato
                                }

                                Image(
                                    painter = painterResource(id = imagenAbrirFormato),
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
                                .width(190.dp)
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
                                val imagenpdf = when (page) {
                                    (0) -> R.drawable.pdfnaranja
                                    (1) -> R.drawable.pdfmorado
                                    (2) -> R.drawable.pdfazul
                                    (3) -> R.drawable.pdfverde
                                    else -> R.drawable.pdfnaranja
                                }
                                Image(
                                    painter = painterResource(id = imagenpdf),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .size(35.dp)
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
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 20.dp),
        )
    }
}


@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun Lol123() {
    NewFormAndOpenPdf(rememberPagerState(), {}, {})
}