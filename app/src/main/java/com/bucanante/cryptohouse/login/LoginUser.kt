package com.bucanante.cryptohouse.login

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.bucanante.cryptohouse.home.HomeActivity
import com.bucanante.cryptohouse.R
import com.bucanante.cryptohouse.register.SignupActivity
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginUser(onLoginClick: () -> Unit) {
    val context = LocalContext.current
    val email = remember { mutableStateOf(TextFieldValue()) }
    val emailErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    lateinit var  auth: FirebaseAuth
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = "Login",
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
        )
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                if (emailErrorState.value) {
                    emailErrorState.value = false
                }
                email.value = it
            },
            isError = emailErrorState.value,
            label = { Text(text = "Username") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_email),
                    contentDescription = "Username",
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
            label = { Text(text = "Password") },
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
                when {
                    email.value.text.isEmpty() -> {
                        emailErrorState.value = true
                    }
                    password.value.text.isEmpty() -> {
                        passwordErrorState.value = true
                    }
                    else -> {
                        passwordErrorState.value = false
                        emailErrorState.value = false
                        auth= FirebaseAuth.getInstance()
                        auth.signInWithEmailAndPassword(email.value.text.toString().trim(), password.value.text.toString().trim())
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    context.startActivity(
                                        Intent(
                                            context,
                                            HomeActivity::class.java
                                        )
                                    )
                                    Toast.makeText(
                                        context,
                                        "Logged in successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Email or Password in incorect",
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
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Don't have an account? ",
                color = Color.Gray,
                modifier = Modifier.padding(start = 60.dp , end = 1.dp)
            )
            Text( modifier = Modifier.clickable {
                context.startActivity(Intent(context, SignupActivity::class.java))
            },
                text = "Register",
                color = Color.Blue,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
            )

        }
        Box(modifier = Modifier.fillMaxSize().padding(bottom = 15.dp)) {
            Text(modifier = Modifier.align(
                Alignment.BottomCenter,
            ),
                text = "Forget Password?",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}