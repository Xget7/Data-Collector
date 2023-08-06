package com.sublimetech.supervisor.presentation.ui.auth.login.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.sublimetech.supervisor.ui.theme.LightRed
import com.sublimetech.supervisor.ui.theme.Red

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    isError: Boolean
) {
    val focusManager = LocalFocusManager.current
    var visibility by remember { mutableStateOf(false) }

    Column {

        OutlinedTextField(
            value = password,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
            ),
            trailingIcon = {
                when (visibility) {
                    (false) -> {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = null,
                            modifier = Modifier.clickable { visibility = true },
                            tint = if (isError) {
                                LightRed
                            } else Color.Gray
                        )
                    }

                    (true) -> {
                        Icon(
                            imageVector = Icons.Default.VisibilityOff,
                            contentDescription = null,
                            modifier = Modifier.clickable { visibility = false },
                            tint = if (isError) {
                                LightRed
                            } else Color.Gray
                        )
                    }
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = if (isError) {
                        LightRed
                    } else Color.Gray
                )
            },
            visualTransformation =
            when (visibility) {
                (false) -> {
                    PasswordVisualTransformation()
                }

                (true) -> {
                    VisualTransformation.None
                }
            },
            singleLine = true,
            onValueChange = {
                onPasswordChange(it)
            },
            placeholder = {
                Text(text = "**********")
            },
            label = {
                Text(
                    text = "Password",
                    modifier = Modifier.padding(start = 4.dp),
                    fontWeight = FontWeight.Bold
                )
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            isError = isError && password.isNotBlank(),
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
                unfocusedIndicatorColor = Color.Gray,
                textColor = MaterialTheme.colors.onBackground,
                placeholderColor = MaterialTheme.colors.onBackground,
            )
        )
        if (isError && password.isNotBlank()) {
            Row(
                modifier = Modifier
                    .padding(vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = Red
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = "Contraseña incorrecta",
                    color = Red
                )
            }
        } else {
            Spacer(modifier = Modifier.padding(15.dp))
        }
    }
}