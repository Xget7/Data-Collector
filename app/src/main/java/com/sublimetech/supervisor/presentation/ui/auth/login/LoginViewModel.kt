package com.sublimetech.supervisor.presentation.ui.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sublimetech.supervisor.domain.useCases.user.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    val repo: UserUseCases,
) : ViewModel() {

    val auth = Firebase.auth

    val currentUserId: String? = Firebase.auth.currentUser?.uid

    private val _state = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()

    init {
        if (currentUserId != null) {
            getUserTypeAndNavigate(currentUserId)
        }
    }

    fun logIn(email: String, password: String) {
        _state.value.isLoading = true
        viewModelScope.launch {

            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                getUserTypeAndNavigate(it.user!!.uid)
                Log.d("MainApp", "Signed LOGIN")
            }.addOnFailureListener { e ->
                var error = ""
                error = when (e as FirebaseException) {
                    is FirebaseAuthInvalidCredentialsException ->
                        "Email o contraseña inválida"

                    is FirebaseAuthInvalidUserException ->
                        "Este usuario no existe"

                    else ->
                        "Error de autenticación"
                }
                _state.update {
                    _state.value.copy(isError = error)
                }


            }

        }
    }

    private fun getUserTypeAndNavigate(uid: String) {
        viewModelScope.launch {
            val result = repo.getUserTypeUseCase.invoke(uid)
            if (result.isSuccess) {
                _state.update {
                    _state.value.copy(authUserType = result.getOrThrow(), isAuthenticated = true)
                }
            } else {
                _state.update {
                    _state.value.copy(
                        isError =
                        result.exceptionOrNull()?.localizedMessage ?: "Error obteniendo usuario."
                    )
                }
            }
            _state.value.isLoading = false
        }
    }


    fun sendPasswordReset(email: String) {
        auth.sendPasswordResetEmail(email).addOnSuccessListener {
            _state.update {
                _state.value.copy(passwordReset = true)
            }
            Log.d("MainApp", "Email enviado correctamente")
        }.addOnFailureListener { e ->
            _state.update {
                _state.value.copy(isError = "Error enviando gmail.")
            }
            Log.d("MainApp", "Email NO ENVIADO")

        }
    }

    fun resetError() {
        _state.value.isError = null
    }


}