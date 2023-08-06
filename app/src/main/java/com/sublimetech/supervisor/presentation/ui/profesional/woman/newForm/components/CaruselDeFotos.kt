package com.sublimetech.supervisor.presentation.ui.profesional.woman.newForm.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.sublimetech.supervisor.domain.model.WomanForm.Deliverables
import com.sublimetech.supervisor.ui.theme.LightOrange
import com.sublimetech.supervisor.ui.theme.Orange

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CaruselDeFotos(
    listaDeEntregables: List<Deliverables>,
    pagerState: PagerState,
    openCamera: () -> Unit,
    onClickEdit: (Deliverables) -> Unit,
    onClickDelete: (Deliverables) -> Unit,
    onChangePhoto: (Deliverables) -> Unit
) {
    Spacer(modifier = Modifier.padding(30.dp))
    Text(
        text = "Evidencias de entregables",
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
    )


    LaunchedEffect(true) {
        Log.d("Recomposed", listaDeEntregables.toString())
    }

    HorizontalPager(
        count = 1 + listaDeEntregables.size,
        state = pagerState,
        modifier = Modifier
            .height(350.dp),
        contentPadding = PaddingValues(start = 250.dp, end = 250.dp),
        itemSpacing = 0.dp,
    ) { page ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page)
                    lerp(
                        start = 0.75f,
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
                    start = 20.dp,
                    end = 20.dp,
                    top = 20.dp,
                    bottom = 10.dp
                )
        ) {
            if (page == 0) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = 10.dp,
                    backgroundColor = MaterialTheme.colors.surface,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(300.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable {
                                openCamera()
                            }
                            .fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(20.dp),
                            tint = MaterialTheme.colors.onBackground
                        )
                        Text(
                            text = "Tomar fotografia",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onBackground,
                            fontSize = 25.sp
                        )
                    }
                }
            } else {

                val currentDeliverable = listaDeEntregables[page - 1]

                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = 10.dp,
                    backgroundColor = MaterialTheme.colors.surface,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(300.dp)
                ) {
                    Box() {
                        Image(
                            painter = rememberAsyncImagePainter(currentDeliverable.image),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(10.dp)
                                .align(Alignment.BottomEnd)
                        ) {
                            Card(
                                shape = CircleShape,
                                backgroundColor = LightOrange,
                                modifier = Modifier
                                    .size(40.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier
                                        .clickable {
                                            onClickDelete(currentDeliverable)
                                        }
                                        .padding(10.dp)
                                        .fillMaxSize()
                                )
                            }
                            Spacer(modifier = Modifier.padding(5.dp))
                            Card(
                                shape = CircleShape,
                                backgroundColor = Orange,
                                modifier = Modifier
                                    .size(50.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier
                                        .clickable { onClickEdit(currentDeliverable) }
                                        .fillMaxSize()
                                        .padding(10.dp)
                                )
                            }
                        }
                        Card(
                            shape = CircleShape,
                            backgroundColor = LightOrange,
                            modifier = Modifier
                                .padding(15.dp)
                                .size(40.dp)
                                .align(Alignment.TopEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Camera,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .clickable {
                                        onChangePhoto(currentDeliverable)
                                    }
                                    .padding(10.dp)
                                    .fillMaxSize()
                            )
                        }
                    }


                }
            }
        }

    }
    Spacer(modifier = Modifier.padding(2.dp))
    HorizontalPagerIndicator(pagerState = pagerState, pageCount = 1 + listaDeEntregables.size)

}