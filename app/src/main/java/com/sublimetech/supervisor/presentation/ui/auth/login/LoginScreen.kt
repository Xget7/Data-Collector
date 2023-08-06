package com.sublimetech.supervisor.presentation.ui.auth.login

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sublimetech.supervisor.R
import com.sublimetech.supervisor.presentation.ui.auth.AuthActivity
import com.sublimetech.supervisor.presentation.ui.auth.login.components.DialogRestartPassword
import com.sublimetech.supervisor.presentation.ui.auth.login.components.EmailTextField
import com.sublimetech.supervisor.presentation.ui.auth.login.components.PasswordTextField
import com.sublimetech.supervisor.presentation.utils.Utils.isValidEmail
import kotlinx.coroutines.*

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(
    vm: LoginViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val orientation = LocalConfiguration.current.orientation

    var email by rememberSaveable { mutableStateOf("professional1@gmail.com") }
    var isEmailError by rememberSaveable { mutableStateOf(false) }

    var password by rememberSaveable { mutableStateOf("professional") }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }

    var isDialogRestartPasswordOpen by rememberSaveable { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()

    val authActivity = LocalContext.current as AuthActivity

    val verticalPadding = when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            30.dp
        }

        else -> {
            100.dp
        }
    }
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        it
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(horizontal = 100.dp, vertical = verticalPadding)
                .fillMaxSize()
                .shadow(elevation = 1.dp, shape = RoundedCornerShape(10))
                .background(MaterialTheme.colors.surface),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            // Imagen superior izquierda
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .size(150.dp)
                    .rotate(180f)
            )


            Column(
                modifier = Modifier
                    .padding(50.dp)
            ) {
                EmailTextField(
                    email = email,
                    onEmailChange = {
                        email = it
                        isEmailError = false
                    },
                    isError = isEmailError
                )

                Spacer(modifier = Modifier.padding(11.dp))

                PasswordTextField(
                    password = password,
                    onPasswordChange = {
                        password = it
                        isPasswordError = false
                    },
                    isError = isPasswordError,
                )

                Button(
                    onClick = {
                        if (email.isBlank()) {
                            // Si el email stá vacio, te mostrará un mensaje y se limpiará el focus
                            focusManager.clearFocus()
                            coroutine.launch {
                                delay(500)
                                Toast.makeText(
                                    context,
                                    "El email no puede estar vacio",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            if (password.isBlank()) {
                                focusManager.clearFocus()
                                coroutine.launch {
                                    delay(500)
                                    Toast.makeText(
                                        context,
                                        "La contraseña no puede estar vacia",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                if (!email.trim().isValidEmail()) {
                                    focusManager.clearFocus()
                                    isEmailError = true
                                    coroutine.launch {
                                        delay(500)
                                        Toast.makeText(context, "Email invalido", Toast.LENGTH_LONG)
                                            .show()
                                    }
                                } else {
                                    vm.logIn(email, password)
                                }
                            }
                        }


                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .shadow(elevation = 1.dp, shape = RoundedCornerShape(20))
                ) {
                    Text(
                        text = "Login",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(horizontal = 100.dp, vertical = 8.dp)
                    )
                }

                Text(
                    text = "Recuperar contraseña",
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp)
                        .clickable {
                            isDialogRestartPasswordOpen = true
                        }
                )
            }


            // Imagen inferior derecha
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .size(150.dp)
                    .align(Alignment.End)
            )

        }

    }

    if (state.passwordReset) {
        LaunchedEffect(true) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = "Email enviado correctamente.",
                actionLabel = null
            )
            Log.d("MainApp", "SNACKCBAR TRUE")
        }
        Log.d("MainApp", "SNACKCBAR L")
    }

    // dialogo de recuperacion de contraseña
    if (isDialogRestartPasswordOpen) {
        DialogRestartPassword(
            onDismissRequest = { isDialogRestartPasswordOpen = false },
            email = email,
            onConfirmClick = {
                if (email.isValidEmail()) vm.sendPasswordReset(email) else Toast.makeText(
                    context,
                    "Email Invalido",
                    Toast.LENGTH_LONG
                ).show()
            }
        )
    }

    if (state.isAuthenticated) {
        authActivity.navigateToMain(
            state.authUserType.userType,
            state.authUserType.professionalType
        )
    }


    if (state.isError != null) {
        Toast.makeText(context, state.isError, Toast.LENGTH_LONG).show()
        vm.resetError()
    }

}