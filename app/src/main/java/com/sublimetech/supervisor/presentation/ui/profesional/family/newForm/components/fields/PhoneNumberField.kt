package com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun PhoneNumber(
    onValueChange: (String) -> Unit,
    label: String,
    onDone: FocusDirection?,
    value: String
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .padding(horizontal = 25.dp, vertical = 0.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            label = { Text(text = label) },
            singleLine = true,
            shape = RoundedCornerShape(30),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor =
                if (value.isNotBlank()) {
                    Color.Black
                } else Color.Gray,
                unfocusedLabelColor = Color.Gray,
                backgroundColor = Color.White
            ),
            trailingIcon = {
                if (value.isNotBlank()) {
                    IconButton(onClick = {
                        onValueChange("")
                    }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (onDone != null) {
                        focusManager.moveFocus(onDone)
                    } else {
                        focusManager.clearFocus()
                    }
                }
            ),
            leadingIcon = {
                Text(text = "+57")
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }
    BackHandler(
        onBack = {
            focusManager.clearFocus()
        }
    )
}
