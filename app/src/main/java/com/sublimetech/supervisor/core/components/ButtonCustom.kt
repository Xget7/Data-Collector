package com.sublimetech.supervisor.core.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sublimetech.supervisor.ui.theme.Orange

@Composable
fun ButtonCustom(
    value: String,
    onClick: () -> Unit,
    enabled: Boolean,
    verticalPadding: Dp = 20.dp
) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(backgroundColor = Orange),
        shape = RoundedCornerShape(40),
        enabled = enabled,
        modifier = Modifier
            .padding(vertical = verticalPadding)
    ) {
        Text(
            text = value,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .width(180.dp)

        )
    }
}