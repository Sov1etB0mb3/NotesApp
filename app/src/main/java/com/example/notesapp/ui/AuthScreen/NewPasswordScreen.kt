package com.example.notesapp.ui.AuthScreen

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notesapp.util.AuthManager
import io.ktor.websocket.Frame
import kotlinx.coroutines.launch

@Composable
fun NewPasswordScreen(navController: NavController) {
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(Modifier.padding(16.dp)) {

        Text("Set New Password", fontSize = 22.sp)

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("New password") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = confirm,
            onValueChange = { confirm = it },
            label = { Text("Confirm") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(mask = '\u25CF')
        )

        if (message != null) Text(message!!, color = Color.Red)

        Button(
            onClick = {
                if (password != confirm) {
                    message = "Passwords do not match"
                    return@Button
                }

                loading = true
                scope.launch {
                    val res = AuthManager.updatePassword(password)
                    loading = false
                    res.onSuccess {
                        navController.navigate("login") {
                            popUpTo("new_password") { inclusive = true }
                        }
                    }.onFailure {
                        message = it.message ?: "Update failed"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (loading) "Updating..." else "Update Password")
        }
    }
}