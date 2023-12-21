package com.bucanante.cryptohouse.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import com.bucanante.cryptohouse.navigation.navigationViewModel.MainScreen

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun HelloWorldScreen() {
    Column {
        Text(text = "Hello, World!")
    }
}