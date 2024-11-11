package com.projects.quickbazaar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

class ScreensControllerHost constructor(navController:NavHostController) {

    var nav_controller:NavHostController=navController
    @Composable
    public fun AppNavHost()
    {
        NavHost(navController = nav_controller, startDestination = "welcome") {
            composable("welcome") { welcomeScreen() }
            composable("login") { loginScreen(nav_controller) }
            composable("signup") { signUpScreen(nav_controller) }
            // Add other screens here, such as home or product details
        }
    }


}