package com.sublimetech.supervisor.presentation.ui.profesional.woman.homeScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sublimetech.supervisor.presentation.utils.Utils.DEPARTAMENTOS


@Composable
fun TopScreen(
    selectedName: String,
    onNameSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val currentComposer = currentComposer.composition


    Column(
        modifier = Modifier.padding(top = 10.dp, start = 40.dp, end = 40.dp)
    ) {
        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        Text(
            text = "Construcción de paz y convivencia",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            text = "Un proyecto de fundacion sublime",
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        Text(
            text = "Departamento",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        Card(
            shape = RoundedCornerShape(20),
            elevation = 2.dp,
            modifier = Modifier.padding(top = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { expanded = true }
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                key(selectedName) {
                    Text(
                        text = selectedName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.height(300.dp)
        ) {
            DEPARTAMENTOS.forEach { name ->
                DropdownMenuItem(
                    onClick = {
                        onNameSelected(name)
                        expanded = false
                        //currentComposer.recompose()
                    },
                ) {
                    Text(text = name.lowercase().capitalize(Locale.current))
                }
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 11.dp))

        Text(
            text = "Municipios",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
    }


}