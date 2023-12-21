package com.bucanante.cryptohouse.register

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bucanante.cryptohouse.R
import com.bucanante.cryptohouse.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignUp(onLoginClick: () -> Unit) {
    val context = LocalContext.current
    val name = remember { mutableStateOf(TextFieldValue()) }
    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val confirmPassword = remember { mutableStateOf(TextFieldValue()) }

    val nameErrorState = remember { mutableStateOf(false) }
    val emailErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val confirmPasswordErrorState = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    lateinit var auth: FirebaseAuth

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = "Sign Up",
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
        )
        OutlinedTextField(
            value = name.value,
            onValueChange = {
                if (nameErrorState.value) {
                    nameErrorState.value = false
                }
                name.value = it
            },
            label = { Text(text = "Enter your Username") },
            isError = nameErrorState.value,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_circle_account),
                    contentDescription = "Username",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(18.dp)
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                if (emailErrorState.value) {
                    emailErrorState.value = false
                }
                email.value = it
            },
            isError = emailErrorState.value,
            label = { Text(text = "Enter email address") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_email),
                    contentDescription = "Enter email address",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(18.dp)
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        val passwordVisibility = remember { mutableStateOf(true) }
        OutlinedTextField(
            value = password.value,
            onValueChange = {
                if (passwordErrorState.value) {
                    passwordErrorState.value = false
                }
                password.value = it
            },
            isError = passwordErrorState.value,
            label = { Text(text = "Enter your Password") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = "Enter your Password",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(18.dp)
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword.value,
            onValueChange = {
                if (confirmPasswordErrorState.value) {
                    confirmPasswordErrorState.value = false
                }
                confirmPassword.value = it
            },
            isError = confirmPasswordErrorState.value,
            label = { Text(text = "Confirm your Password") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = "Password",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(18.dp)
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                if (confirmPasswordErrorState.value) {
                    val msg = if (confirmPassword.value.text.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Required",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (confirmPassword.value.text != password.value.text) {
                        Toast.makeText(
                            context,
                            "Password not matching",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        ""
                    }
                }
                when {
                    name.value.text.isEmpty() -> {
                        nameErrorState.value = true
                    }
                    email.value.text.toString().isEmpty() -> {
                        emailErrorState.value = true
                    }
                    password.value.text.isEmpty() -> {
                        passwordErrorState.value = true
                    }
                    confirmPassword.value.text.isEmpty() -> {
                        confirmPasswordErrorState.value = true
                    }
                    confirmPassword.value.text != password.value.text -> {
                        confirmPasswordErrorState.value = true
                    }
                    else -> {
                        auth = FirebaseAuth.getInstance()
                        auth.createUserWithEmailAndPassword(
                            email.value.text.toString().trim(),
                            password.value.text.toString().trim()
                        )
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    context.startActivity(
                                        Intent(
                                            context,
                                            LoginActivity::class.java
                                        )
                                    )
                                    Toast.makeText(
                                        context,
                                        "Registered successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    task.exception?.let { Log.wtf("test", it) }
                                    Toast.makeText(
                                        context,
                                        "Registered not found",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Sign Up",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Already have an account? ",
                color = Color.Gray,
                modifier = Modifier.padding(start = 60.dp, end = 1.dp)
            )
            Text(
                modifier = Modifier.clickable {
                    context.startActivity(Intent(context, LoginActivity::class.java))
                },
                text = "Login",
                color = Color.Blue,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
            )

        }
    }
}