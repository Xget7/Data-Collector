package com.sublimetech.supervisor.presentation.ui.auth.login.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DialogRestartPassword(
    onDismissRequest: () -> Unit,
    email: String,
    onConfirmClick: () -> Unit
) {

    val corotine = rememberCoroutineScope()
    val context = LocalContext.current
    Dialog(
        onDismissRequest = { }
    ) {
        Card(
            shape = RoundedCornerShape(10),
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(30.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Recuperacion de contraseña",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.padding(6.dp))

                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("Se enviará un link de recuperacion de contraseña al siguiente correo electronico: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(email)
                        }
                    }
                )

                Spacer(modifier = Modifier.padding(10.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    OutlinedButton(onClick = {
                        onDismissRequest()
                    }) {
                        Text(
                            text = "Cancelar",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(3.dp)
                        )
                    }
                    Button(onClick = {
                        onConfirmClick()
                        onDismissRequest()
                        corotine.launch {
                            delay(300)
                            Toast.makeText(
                                context,
                                "Link de recuperacion enviado",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }) {
                        Text(
                            text = "Confirmar",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(3.dp)
                        )
                    }
                }
            }
        }
    }
}