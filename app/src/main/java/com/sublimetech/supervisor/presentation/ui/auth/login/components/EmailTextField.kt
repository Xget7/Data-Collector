package com.sublimetech.supervisor.presentation.ui.auth.login.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.github.skgmn.composetooltip.AnchorEdge
import com.github.skgmn.composetooltip.EdgePosition
import com.github.skgmn.composetooltip.Tooltip
import com.github.skgmn.composetooltip.rememberTooltipStyle
import com.sublimetech.supervisor.ui.theme.LightRed
import com.sublimetech.supervisor.ui.theme.Red
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EmailTextField(
    email: String,
    onEmailChange: (String) -> Unit,
    isError: Boolean
) {
    val focusManager = LocalFocusManager.current
    val coroutine = rememberCoroutineScope()

    Column {
        if (isError && email.isNotBlank()) {

            var mostrarTooltip by remember { mutableStateOf(false) }

            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = Red,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .clip(CircleShape)
                    .clickable {
                        mostrarTooltip = true
                        coroutine.launch {
                            delay(10000)
                            mostrarTooltip = false
                        }
                    }
                    .align(Alignment.End)
            )

            if (mostrarTooltip) {
                Tooltip(
                    anchorEdge = AnchorEdge.Top,
                    tipPosition = EdgePosition(0.9f),
                    anchorPosition = EdgePosition(0.954f),
                    tooltipStyle = rememberTooltipStyle(
                        color = Color.LightGray,
                    ),
                    modifier = Modifier
                        .width(590.dp)
                ) {
                    Text(
                        "Por favor asegúrese de que no contenga los siguientes caracteres no permitidos:\n" +
                                "\n" +
                                "Caracteres especiales como: ! # \$ % ^ & * + = { } | \\ < > ? ~\n" +
                                "Espacios en blanco al principio o al final del email\n" +
                                "Más de una arroba (@) en la dirección de email\n" +
                                "Falta el nombre de usuario o el dominio después de la arroba (@)"
                    )
                }
            }


        } else {
            Spacer(modifier = Modifier.padding(12.dp))
        }
        OutlinedTextField(
            value = email,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
            ),
            singleLine = true,
            onValueChange = {
                onEmailChange(it)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = if (isError) {
                        LightRed
                    } else Color.Gray
                )
            },
            placeholder = {
                Text(text = "ejemplo123@hotmail.com")
            },
            label = {
                Text(
                    text = "Email",
                    modifier = Modifier.padding(start = 4.dp),
                    fontWeight = FontWeight.Bold
                )
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.clickable { onEmailChange("") },
                    tint = if (isError) {
                        LightRed
                    } else Color.Gray
                )
            },
            isError = isError && email.isNotBlank(),
            shape = RoundedCornerShape(20),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End),
            colors = TextFieldDefaults.textFieldColors(
                errorLabelColor = Red,
                errorIndicatorColor = Red,
                errorCursorColor = Red,
                backgroundColor = MaterialTheme.colors.background,
                unfocusedLabelColor = Color.Gray,
                focusedLabelColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = MaterialTheme.colors.primary,
                placeholderColor = MaterialTheme.colors.onBackground,
                unfocusedIndicatorColor = Color.Gray,
                textColor = if (isError) {
                    LightRed
                } else MaterialTheme.colors.onBackground
            )
        )
    }
}