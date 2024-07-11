//package com.aco.usedoilcollection.ui
//
//import android.graphics.Typeface
//import android.view.View
//import android.widget.Button
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.ComposeView
//import androidx.compose.ui.platform.testTag
//import androidx.compose.ui.semantics.contentDescription
//import androidx.compose.ui.semantics.semantics
//import androidx.compose.ui.semantics.testTag
//import androidx.compose.ui.text.input.KeyboardCapitalization
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.core.content.ContextCompat
//import com.aco.usedoilcollection.R
//import com.aco.usedoilcollection.viewmodel.AuthViewModel
//
//
//@Composable
//fun AuthScreen(viewModel: AuthViewModel, onAuthSuccess: () -> Unit, toggleButtonId: Int) {
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var name by remember { mutableStateOf("") }
//    var isLoginMode by remember { mutableStateOf(true) }
//    var errorMessage by remember { mutableStateOf<String?>(null) }
//
//    fun isValidEmail(email: String): Boolean {
//        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        if (!isLoginMode) {
//            TextField(
//                value = name,
//                onValueChange = { name = it },
//                label = { Text("Name") },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words),
//                modifier = Modifier.widthIn(max = 300.dp).semantics { testTag = "name_field" }
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//        }
//        TextField(
//            value = email,
//            onValueChange = { email = it },
//            label = { Text("Email") },
//            singleLine = true,
//            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
//            modifier = Modifier.testTag("email_field")
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        TextField(
//            value = password,
//            onValueChange = { password = it },
//            label = { Text("Password") },
//            singleLine = true,
//            visualTransformation = PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
//            modifier = Modifier.testTag("password_field")
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = {
//            errorMessage = when {
//                email.isEmpty() || password.isEmpty() || (!isLoginMode && name.isEmpty()) -> "All fields are required"
//                !isValidEmail(email) -> "Invalid email address"
//                else -> null
//            }
//
//            if (errorMessage == null) {
//                if (isLoginMode) {
//                    viewModel.login(email, password, onSuccess = onAuthSuccess) { error ->
//                        errorMessage = error
//                    }
//                } else {
//                    viewModel.register(email, password, name, onSuccess = onAuthSuccess) { error ->
//                        errorMessage = error
//                    }
//                }
//            }
//        },
//            modifier = Modifier.testTag("create_account_button")
//        ) {
//            Text(if (isLoginMode) "Sign In" else "Create Account")
//        }
//        Spacer(modifier = Modifier.height(8.dp))
//
//        @Composable
//        fun CustomStyledButton(isLoginMode: Boolean, onClick: () -> Unit) {
//            TextButton(onClick = onClick) {
//                Text(
//                    text = if (isLoginMode) "Don't have an account? Create Account" else "Already have an account? Sign In",
//                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
//                )
//            }
//        }
//
//            TextButton(onClick = { isLoginMode = !isLoginMode }) {
//            Text(if (isLoginMode) "Don't have an account? Create Account" else "Already have an account? Sign In")
//        }
//
//        errorMessage?.let {
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = it, color = Color.Red)
//        }
//
//        Spacer(modifier = Modifier.height(150.dp))
//    }
//}