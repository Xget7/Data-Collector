package com.sublimetech.supervisor.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sublimetech.supervisor.MainActivity
import com.sublimetech.supervisor.presentation.ui.auth.login.LoginScreen
import com.sublimetech.supervisor.ui.theme.SupervisorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SupervisorTheme {
                LoginScreen()
            }
        }
    }

    fun navigateToMain(userType: String, professionalType: String) {
        Log.d("MainApp", "navigateToMain $userType")
        Log.d("MainApp", "navigateToMain $professionalType")
        val i = Intent(this, MainActivity::class.java)
        i.putExtra("userType", userType)
        i.putExtra("profession", professionalType)
        startActivity(i)
        finish()
    }
}



