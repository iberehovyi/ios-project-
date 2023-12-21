package com.bucanante.cryptohouse.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen (
    val route: String,
    val title: String,
    val image: ImageVector
        ) {
    object Home: BottomBarScreen(
        route = "home",
        title = "Home",
        image = Icons.Default.Home
            )
    object Portfolio: BottomBarScreen(
        route = "portfolio",
        title = "Portfolio",
        image = Icons.Default.List
    )
    object Profile: BottomBarScreen(
        route = "profile",
        title = "Profile",
        image = Icons.Default.Person
    )
    object CryptoDetails: BottomBarScreen(
        route = "cryptoDetails/{coinId}/{coinName}/{coinPrice}/{coinImage}",
        title = "CryptoDetails",
        image = Icons.Default.Home
    ) {
        fun createRoute(coinId: String, coinName: String, coinPrice: String, coinImage: String): String =
            "cryptoDetails/$coinId/$coinName/$coinPrice/${coinImage.replace("/", "%2F")}"
    }
}
