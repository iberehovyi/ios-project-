package com.bucanante.cryptohouse.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bucanante.cryptohouse.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import com.bucanante.cryptohouse.screens.firebase.logoutUser

@Composable
fun ProfileScreen() {
    val user = FirebaseAuth.getInstance().currentUser
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Add this line
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = 4.dp,
            modifier = Modifier
                .padding(15.dp)
                .align(Alignment.TopCenter)
        ) {
            Column(
                modifier = Modifier.padding(15.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome back ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                )
                Text(
                    text = "${user?.email ?: "unknown"}",
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(25.dp))
                Image(
                    painter = painterResource(id = com.bucanante.cryptohouse.R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(68.dp)
                        .padding(start = 1.dp)
                )
                LogoutButton(modifier = Modifier.padding(top =30.dp, bottom = 10.dp))
            }
        }
    }
}

@Composable
fun LogoutButton(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    OutlinedButton(
        modifier = modifier
            .width(150.dp)
            .height(56.dp)
            .padding(horizontal = 10.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primaryVariant,
                shape = RoundedCornerShape(4.dp)
            ),
        onClick = {
            logoutUser()
            context.startActivity(Intent(context, LoginActivity::class.java))
            Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
        },
        shape = MaterialTheme.shapes.medium,
    ) {
        Icon(
            imageVector = Icons.Default.ExitToApp,
            contentDescription = "Logout",
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = "Logout",
            style = MaterialTheme.typography.button,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}






