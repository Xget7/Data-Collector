package com.sublimetech.supervisor.presentation.ui.profesional.woman.newForm.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Counter(
    count: Int,
    onCountChange: (Int) -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.width(240.dp)
    ) {
        Button(
            onClick = { onCountChange(count - 1) },
            shape = RoundedCornerShape(10.dp),
            enabled = count > 1
        ) {

            Text(
                text = "-",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }

        OutlinedTextField(
            value = count.toString(),
            onValueChange = { newValue ->
                val intValue = newValue.toIntOrNull() ?: 1
                onCountChange(intValue.coerceAtLeast(1))
            },
            maxLines = 1,
            textStyle = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Gray
            ),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.widthIn(max = 80.dp),
            shape = RoundedCornerShape(10.dp)
        )


        Button(
            onClick = { onCountChange(count + 1) },
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "+",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }
    }
}