package com.sublimetech.supervisor.presentation.ui.profesional.family.townDetails.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FamilyCircularProgress(
    percent: Float,
    value: Int,
    fontSize: TextUnit = 28.sp,
    radio: Dp = 100.dp,
    color: Color,
    strokeWidth: Dp = 15.dp,
    total: Int,
    completed: Int,
    page: Int,
    onNext: () -> Unit,
    onBack: () -> Unit
) {


    var animationPlayed by remember { mutableStateOf(false) }
    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percent else 0f,
        animationSpec = tween(
            durationMillis = 500,
            delayMillis = 5
        )
    )

    val colorArc = MaterialTheme.colors.surface

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Column {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(radio * 2f)
        ) {
            Canvas(modifier = Modifier.size(radio * 2f)) {
                drawArc(
                    color = colorArc,
                    -90f,
                    360f,
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
            }
            Canvas(modifier = Modifier.size(radio * 2f)) {
                drawArc(
                    color = color,
                    -90f,
                    if (curPercentage.value != 0f) 360 * curPercentage.value else 360 * 0.001f,
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${(curPercentage.value * value).toInt()}%",
                    color = Color.Black,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold
                )

                Text(text = "$completed / $total")
            }


            if (page > 0) {
                IconButton(
                    onClick = {
                        onBack()
                    },
                    Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowLeft,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier
                            .size(44.dp)
                    )
                }
            }


            if (page < 2) {
                IconButton(
                    onClick = {
                        onNext()
                    },
                    Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowRight,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier
                            .size(44.dp)
                    )
                }
            }


        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(radio * 2f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.RadioButtonChecked,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier
                        .size(34.dp)
                )

                Text(
                    text = " Momento # ${page + 1}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

            }

        }


    }


}