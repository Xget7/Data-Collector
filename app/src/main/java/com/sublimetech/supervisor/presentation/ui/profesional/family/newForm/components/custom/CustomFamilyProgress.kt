package com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.custom

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sublimetech.supervisor.ui.theme.Orange

@Composable
fun CustomFamilyProgress(
    modifier: Modifier,
    width: Dp = 200.dp,
    backgroundColor: Color,
    foregroundColor: Brush,
    percent: Float,
) {

    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percent else 0f,
        animationSpec = tween(
            durationMillis = 1500,
            delayMillis = 0
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 10.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.RadioButtonChecked,
                contentDescription = null,
                tint = if (curPercentage.value <= 0.38f) Orange else Color.LightGray,
                modifier = Modifier
                    .size(30.dp)
            )
            Text(
                text = "Momento #1",
                fontWeight = FontWeight.Bold,
                color = if (curPercentage.value <= 0.38f) Orange else Color.LightGray
            )
            Icon(
                imageVector = Icons.Default.RadioButtonChecked,
                contentDescription = null,
                tint = if (curPercentage.value > 0.38f && curPercentage.value <= 0.737f) Orange else Color.LightGray,
                modifier = Modifier
                    .size(30.dp)
            )
            Text(
                text = "Momento #2",
                fontWeight = FontWeight.Bold,
                color = if (curPercentage.value > 0.38f && curPercentage.value <= 0.737f) Orange else Color.LightGray
            )
            Icon(
                imageVector = Icons.Default.RadioButtonChecked,
                contentDescription = null,
                tint = if (curPercentage.value > 0.737f) Orange else Color.LightGray,
                modifier = Modifier
                    .size(30.dp)
            )
            Text(
                text = "Momento #3",
                fontWeight = FontWeight.Bold,
                color = if (curPercentage.value > 0.737f) Orange else Color.LightGray
            )

        }

        Box(
            modifier = modifier
                .background(backgroundColor)
                .width(width)
        ) {
            Box(
                modifier = modifier
                    .background(foregroundColor)
                    .width(width * curPercentage.value)
            )
        }
    }
}