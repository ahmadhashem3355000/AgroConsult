package com.agroconsult.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.agroconsult.app.ui.screens.auth.LoginScreen
import com.agroconsult.app.ui.screens.auth.RegisterScreen
import com.agroconsult.app.ui.screens.home.HomeScreen
import com.agroconsult.app.ui.screens.marketplace.MarketplaceScreen
import com.agroconsult.app.ui.screens.consultations.ConsultationsScreen
import com.agroconsult.app.ui.screens.messages.MessagesScreen
import com.agroconsult.app.ui.screens.profile.ProfileScreen

object AppRoutes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val MARKETPLACE = "marketplace"
    const val CONSULTATIONS = "consultations"
    const val MESSAGES = "messages"
    const val PROFILE = "profile"
}

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.LOGIN
    ) {
        composable(AppRoutes.LOGIN) {
            LoginScreen(navController)
        }
        composable(AppRoutes.REGISTER) {
            RegisterScreen(navController)
        }
        composable(AppRoutes.HOME) {
            HomeScreen(navController)
        }
        composable(AppRoutes.MARKETPLACE) {
            MarketplaceScreen(navController)
        }
        composable(AppRoutes.CONSULTATIONS) {
            ConsultationsScreen(navController)
        }
        composable(AppRoutes.MESSAGES) {
            MessagesScreen(navController)
        }
        composable(AppRoutes.PROFILE) {
            ProfileScreen(navController)
        }
    }
}
