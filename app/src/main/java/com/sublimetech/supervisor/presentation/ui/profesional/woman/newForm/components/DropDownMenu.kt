package com.sublimetech.supervisor.presentation.ui.profesional.woman.newForm.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DropDownMenu(
    listaDeOpciones: List<String>,
    opcionSeleccionada: String,
    onSelectedOption: (String) -> Unit,
    title: String
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Column() {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
            Card(
                shape = RoundedCornerShape(20),
                elevation = 5.dp,
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .clickable { expanded = true }
                        .width(240.dp)
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    key(opcionSeleccionada) {
                        Text(
                            text = opcionSeleccionada,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(240.dp)
        ) {
            listaDeOpciones.forEach { opcion ->
                DropdownMenuItem(
                    onClick = {
                        onSelectedOption(opcion)
                        expanded = false
                        //currentComposer.recompose()

                    }
                ) {
                    Text(text = opcion)
                }
            }
        }
    }
}
