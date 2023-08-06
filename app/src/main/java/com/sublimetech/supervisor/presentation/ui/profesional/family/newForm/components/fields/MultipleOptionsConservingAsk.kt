package com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun MultipleOptionsConservingAsk(
    label: String,
    returnSelectedItem: (String) -> Unit,
    enabled: Boolean,
    selected: String,
    listaDeInstituciones: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }


    Column(
        modifier = Modifier
            .padding(horizontal = 25.dp, vertical = 10.dp)
    ) {

        Card(
            shape = RoundedCornerShape(30),
            elevation = 0.dp,
            backgroundColor = Color.White,
            modifier = Modifier
                .border(1.dp, Color.Gray, RoundedCornerShape(30))
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
        ) {
            Text(
                text = "$label: $selected",
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = if (selected.isNotBlank()) Color.Black else Color.Gray,
                modifier = Modifier
                    .clickable {
                        if (enabled) {
                            expanded = !expanded
                        }

                    }
                    .padding(15.dp)
                    .fillMaxWidth()
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(600.dp)
            ) {
                listaDeInstituciones.forEach {
                    DropdownMenuItem(onClick = {
                        returnSelectedItem(it)
                        expanded = false
                    }) {
                        Text(
                            text = it,
                        )
                    }
                }
            }
        }
    }
}
