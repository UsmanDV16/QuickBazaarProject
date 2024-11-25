package com.projects.quickbazaar
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument

class ScreensControllerHost constructor(navController:NavHostController) {

    var nav_controller:NavHostController=navController
    @Composable
    public fun AppNavHost()
    {
        val bottomNavDestinations = listOf("home", "categories")
        Scaffold(
            bottomBar = {
                val currentDestination = nav_controller.currentBackStackEntryAsState().value?.destination?.route
                if (currentDestination in bottomNavDestinations) {

                    BottomNavigationBar(nav_controller)
                }
            },
            topBar={
                val currentDestination = nav_controller.currentBackStackEntryAsState().value?.destination?.route
                if (currentDestination in bottomNavDestinations) {

                    TopBar(nav_controller)
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = nav_controller,
                startDestination = "welcome",
                modifier = Modifier.padding(paddingValues)
            ) {
                // Define all your composable destinations
                composable("welcome") { WelcomeScreen(nav_controller) }
                composable("login") { LoginScreen(nav_controller) }
                composable("signup") { SignUpScreen(nav_controller) }
                composable("home") { HomeScreen(nav_controller) }
                composable("categories") { CategoriesScreen(
                    nav_controller,
                    CategoryViewModel())
                }
                composable("categoryProducts/{categoryId}/{categoryName}") { backStackEntry ->
                    val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                    val categoryId=backStackEntry.arguments?.getString("categoryId")?:""
                    CategoryProductsScreen(nav_controller, categoryName, categoryProductsViewModel = CategoryProductsViewModel(categoryId))
                }
                composable("newArrivals") { NewArrivalsScreen(
                    navController = nav_controller,
                    newArrivalsViewModel = NewArrivalsViewModel()
                ) }
            }
        }

    }


}
