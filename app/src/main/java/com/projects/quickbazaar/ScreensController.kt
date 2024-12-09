package com.projects.quickbazaar
import OrderHistoryScreen
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument

class ScreensControllerHost constructor(navController:NavHostController, networkMonitor: NetworkMonitor) {


    var network_monitor:NetworkMonitor=networkMonitor
    var nav_controller:NavHostController=navController
    val isConnected = network_monitor.isConnected
    val homeViewModel = HomeViewModel()
    val accountUpdateViewModel=AccountUpdateViewModel()

    var loginViewModel=LoginViewModel()
    var signUpViewModel=SignUpViewModel()
    var categoryViewModel=CategoryViewModel()
    var categoryProductsViewModel=CategoryProductsViewModel()
    var changePasswordViewModel=ChangePasswordViewModel()
    var orderHistoryViewModel=OrderHistoryViewModel()
    var trackOrderViewModel=TrackOrderViewModel()
    var newArrivalsViewModel=NewArrivalsViewModel()
    var productViewModel=ProductDetailViewModel()
    var resetPasswordViewModel=ResetPasswordViewModel()
    var cartViewModel = CartViewModel()
    var checkoutViewModel=CheckoutViewModel()

    public fun  reset()
    {
        loginViewModel=LoginViewModel()
        signUpViewModel=SignUpViewModel()
        categoryViewModel=CategoryViewModel()
        categoryProductsViewModel=CategoryProductsViewModel()
        changePasswordViewModel=ChangePasswordViewModel()
        orderHistoryViewModel=OrderHistoryViewModel()
        trackOrderViewModel=TrackOrderViewModel()
        newArrivalsViewModel=NewArrivalsViewModel()
        productViewModel=ProductDetailViewModel()
        resetPasswordViewModel=ResetPasswordViewModel()
        cartViewModel = CartViewModel()
        checkoutViewModel=CheckoutViewModel()

    }

    @Composable
    public fun AppNavHost(context: Context)
    {


        val bottomNavDestinations = listOf("home", "categories")
        val inSearch=listOf("search/{searchQuery}", "searching")



        if (!isConnected.value!!) {
            // Navigate to "No Internet" screen
            nav_controller.navigate("noInternet") {
                popUpTo(nav_controller.graph.startDestinationId) { inclusive = true }
            }

        }
        if (isConnected.value!!&& nav_controller.currentDestination?.route =="noInternet") {
            nav_controller.popBackStack() // Go back to the previous screen
        }

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
                    val state= remember {
                        mutableStateOf(false)
                    }

                    TopBar(
                        navController = nav_controller,
                        isSearchMode = state
                    )
                }
                else if(currentDestination in inSearch)
                {
                    val state= remember {
                        mutableStateOf(true)
                    }

                    TopBar(
                        navController = nav_controller,
                        isSearchMode = state

                    )
                }

            }
        ) { paddingValues ->
            NavHost(
                navController = nav_controller,
                startDestination = "appEntryPoint",
                modifier = Modifier.padding(paddingValues)
            ) {
                composable("appEntryPoint"){ AppEntryPoint(context, nav_controller) }
                // Define all your composable destinations
                composable("welcome") { WelcomeScreen(nav_controller) }
                composable("login") {
                    LoginScreen(navController = nav_controller, loginViewModel) }
                composable("signup") { SignUpScreen(nav_controller, signUpViewModel) }
                composable("home") {
                    HomeScreen(nav_controller, false, false, "", homeViewModel) }
                composable("searching"){ HomeScreen(nav_controller, true, false, "", homeViewModel)
                }
                composable("search/{searchQuery}") { backStackEntry ->
                    val query = backStackEntry.arguments?.getString("searchQuery") ?: ""
                    HomeScreen(
                    nav_controller,
                    false,
                    true,
                    searchQuery = query,
                        homeViewModel
                ) }
                composable("categories") {
                    CategoriesScreen(
                        nav_controller,
                        categoryViewModel)
                }
                composable("categoryProducts/{cId}/{categoryName}") { backStackEntry ->
                    val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                    val categoryId = backStackEntry.arguments?.getString("cId") ?: ""

                    CategoryProductsScreen(
                        nav_controller,
                        categoryId,
                        categoryName,
                        categoryProductsViewModel
                    )
                }
                composable("newArrivals") {
                    NewArrivalsScreen(
                        navController = nav_controller,
                        newArrivalsViewModel
                    )
                }
                composable("accountManagement") { AccountManagementScreen(navController = nav_controller) }
                composable("productDetails/{pId}") { backStackEntry ->
                    val pId = backStackEntry.arguments?.getString("pId") ?: ""
                    ProductScreen(
                        nav_controller,
                        pId,
                        productViewModel
                    )
                }
                composable("infoUpdate") {
                    AccountInfoUpdateScreen(
                        nav_controller,
                        accountUpdateViewModel
                    )
                }
                composable("changePass") {
                    ChangePasswordScreen(nav_controller, changePasswordViewModel)
                }
                composable("trackOrder/{oId}") { backStackEntry ->
                    val oId = backStackEntry.arguments?.getString("oId") ?: ""
                    TrackOrderScreen(navController = nav_controller, orderId = oId, trackOrderViewModel)
                }
                composable("orderHistory/{uId}") { backStackEntry ->
                    val uId=backStackEntry.arguments?.getString("uId")?:""
                    OrderHistoryScreen(navController = nav_controller, userId = uId, orderHistoryViewModel)
                }
                composable("cart/{uId}") { backStackEntry ->
                    val uId=backStackEntry.arguments?.getString("uId")?:""
                    CartScreen(userId = uId, navController = nav_controller, cartViewModel)
                }
                composable("checkout/{uId}/{subtotal}/{dc}")
                {
                        backStackEntry ->
                    val uId=backStackEntry.arguments?.getString("uId")?:""
                    val subtotal =backStackEntry.arguments?.getString("subtotal")?.toInt()?:0
                    val deliveryCharges=backStackEntry.arguments?.getString("dc")?.toInt()?:0
                    CheckoutScreen(userId = uId, subtotal = subtotal, deliveryCharges = deliveryCharges, viewModel = checkoutViewModel, navController = nav_controller)
                }
                composable("resetPassword"){
                    ResetPasswordScreen(navController = nav_controller, resetPasswordViewModel = resetPasswordViewModel)
                }
                composable("noInternet"){NoInternetScreen(nav_controller, onQuit = {
                    // Quit the app
                    (context as? Activity)?.finish()
                })}
                composable("orderPlaced/{timeAndDate}/{oId}") {
                        backStackEntry ->
                    val timeAndDate=backStackEntry.arguments?.getString("timeAndDate")?:""
                    val oId =backStackEntry.arguments?.getString("oId")?:""
                    OrderPlacedScreen(nav_controller, timeAndDate, oId)
                }
            }

        }

    }


}
