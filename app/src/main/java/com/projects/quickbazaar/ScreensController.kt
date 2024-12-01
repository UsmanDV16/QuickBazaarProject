package com.projects.quickbazaar
import OrderHistoryScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
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
            topBar = {
                val currentDestination = nav_controller.currentBackStackEntryAsState().value?.destination?.route

                if (currentDestination in bottomNavDestinations) {
                    val state1= remember {
                        mutableStateOf(false)
                    }
                    val state2= remember {
                        mutableStateOf("")
                    }
                    TopBar(
                        navController = nav_controller,
                        isSearchMode = state1,
                        searchQuery = state2,
                        onSearch = {

                        }
                    )
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
                composable("login") { LoginScreen(nav_controller, LoginViewModel()) }
                composable("signup") { SignUpScreen(nav_controller, SignUpViewModel()) }
                composable("home") { HomeScreen(nav_controller, false, "", HomeViewModel()) }
                composable("categories") {
                    CategoriesScreen(
                        nav_controller,
                        CategoryViewModel()
                    )
                }
                composable("categoryProducts/{categoryId}/{categoryName}") { backStackEntry ->
                    val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                    val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
                    CategoryProductsScreen(
                        nav_controller,
                        categoryName,
                        categoryProductsViewModel = CategoryProductsViewModel(categoryId)
                    )
                }
                composable("newArrivals") {
                    NewArrivalsScreen(
                        navController = nav_controller,
                        newArrivalsViewModel = NewArrivalsViewModel()
                    )
                }
                composable("accountManagement") { AccountManagementScreen(navController = nav_controller) }
                composable("productDetails/{pId}") { backStackEntry ->
                    val pId = backStackEntry.arguments?.getString("pId") ?: ""
                    ProductScreen(
                        nav_controller,
                        pId,
                        productViewModel = ProductDetailViewModel(pId)
                    )
                }
                composable("infoUpdate") {
                    AccountInfoUpdateScreen(
                        nav_controller,
                        AccountUpdateViewModel()
                    )
                }
                composable("changePass") {
                    ChangePasswordScreen(nav_controller, ChangePasswordViewModel())
                }
                composable("trackOrder/{oId}") { backStackEntry ->
                    val oId = backStackEntry.arguments?.getString("oId") ?: ""
                    TrackOrderScreen(navController = nav_controller, orderId = oId)
                }
                composable("orderHistory/{uId}") { backStackEntry ->
                    val uId=backStackEntry.arguments?.getString("uId")?:""
                    OrderHistoryScreen(navController = nav_controller, userId = uId)
                }
            }

        }

    }


}
