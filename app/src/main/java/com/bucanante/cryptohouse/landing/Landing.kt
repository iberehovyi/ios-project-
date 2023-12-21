package com.bucanante.cryptohouse.landing

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bucanante.cryptohouse.login.LoginActivity
import com.bucanante.cryptohouse.register.SignupActivity

@Composable
fun LandingScreen() {
    val context = LocalContext.current
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 120.dp)
        ) {
            Image(
                painter = painterResource(id = com.bucanante.cryptohouse.R.drawable.bitcoinbg),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(350.dp)
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Save your crypto",
            style = MaterialTheme.typography.h5,
            fontSize = 30.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(horizontal = 10.dp),
        )
        Row(
            modifier = Modifier.padding(horizontal = 10.dp),
        ) {
            Text(
                text = "portfolio",
                style = MaterialTheme.typography.subtitle1,
                fontSize = 30.sp,
                fontWeight = FontWeight.Normal,
            )
            Image(
                painter = painterResource(id = com.bucanante.cryptohouse.R.drawable.right_arow),
                contentDescription = "Arrow",
                modifier = Modifier
                    .size(55.dp)
                    .padding(start = 10.dp)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 10.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.primaryVariant,
                    shape = RoundedCornerShape(4.dp)
                ),
            onClick = {
                context.startActivity(Intent(context, LoginActivity::class.java))
            }) {
            Text(
                text = "Login",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 10.dp),
            onClick = {
                context.startActivity(Intent(context, SignupActivity::class.java))
            }) {
            Text(
                text = "Sign Up",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }

    }

}