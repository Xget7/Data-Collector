package com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun OptionAndText(
    onValueChange: (String) -> Unit,
    label: String,
    onDone: FocusDirection?,
    labelMultipleOption: String,
    returnSelectedItem: (String) -> Unit,
    options: List<String>,
    value: String,
    selectedItem: String
) {

    val focusManager = LocalFocusManager.current
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .padding(horizontal = 25.dp, vertical = 5.dp)
    ) {
        Column(
            modifier = Modifier.padding(end = 10.dp)
        ) {

            OutlinedButton(
                onClick = { expanded = !expanded },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                ),
                shape = RoundedCornerShape(30),
                modifier = Modifier
                    .height(56.dp)
                    .border(color = Color.Gray, width = 1.dp, shape = RoundedCornerShape(30))
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    }
            ) {
                Text(
                    text = selectedItem.ifBlank { labelMultipleOption },
                    color = if (selectedItem != "") {
                        Color.Black
                    } else Color.Gray

                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
            )
            {
                options.forEach { label ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            returnSelectedItem(label)
                        }
                    ) {
                        Text(text = label)
                    }
                }
            }
        }

        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            label = { Text(text = label) },
            singleLine = true,
            shape = RoundedCornerShape(30),
            trailingIcon = {
                if (value.isNotBlank()) {
                    IconButton(onClick = {
                        onValueChange("")
                    }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (onDone != null) {
                        focusManager.moveFocus(onDone)
                    } else {
                        focusManager.clearFocus()
                    }
                }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor =
                if (value.isNotBlank()) {
                    Color.Black
                } else Color.Gray,
                unfocusedLabelColor = Color.Gray,
                backgroundColor = Color.White,
                disabledTextColor = Color.Black
            ),
            modifier = Modifier
                .weight(1f)
        )
    }
    BackHandler(
        onBack = {
            focusManager.clearFocus()
        }
    )
}