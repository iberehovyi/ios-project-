package com.bucanante.cryptohouse.annotations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bucanante.cryptohouse.navigation.BottomBarScreen
import com.bucanante.cryptohouse.screens.*

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomBarScreen.Home.route) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController)
        }
        composable(route = BottomBarScreen.Portfolio.route) {
            PortfolioScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
        composable(
            route = BottomBarScreen.CryptoDetails.route,
            arguments = listOf(
                navArgument("coinId") { type = NavType.StringType },
                navArgument("coinName") { type = NavType.StringType },
                navArgument("coinPrice") { type = NavType.StringType },
                navArgument("coinImage") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val coinId = backStackEntry.arguments?.getString("coinId") ?: ""
            val coinName = backStackEntry.arguments?.getString("coinName") ?: ""
            val coinPrice = backStackEntry.arguments?.getString("coinPrice") ?: ""
            val coinImage = backStackEntry.arguments?.getString("coinImage") ?: ""

            CryptoDetailsScreen(coinId, coinName, coinPrice, coinImage)
        }
    }
}