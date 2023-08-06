package com.sublimetech.supervisor.presentation.ui.profesional.woman.townDetails.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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

@Composable
fun CircularProgress(
    porcentage: Float,
    valor: Int,
    fontSize: TextUnit = 28.sp,
    radio: Dp = 100.dp,
    color: Color,
    strokeWidth: Dp = 15.dp,
    duracionAnimacion: Int = 1000,
    delayAnimacion: Int = 0
) {
    var animationPlayed by remember { mutableStateOf(false) }
    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) porcentage else 0f,
        animationSpec = tween(
            durationMillis = duracionAnimacion,
            delayMillis = delayAnimacion
        )
    )
    val colorArc = MaterialTheme.colors.surface
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
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
        Text(
            text = "${(curPercentage.value * valor).toInt()}%",
            color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}


