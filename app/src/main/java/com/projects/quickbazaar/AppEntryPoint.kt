package com.projects.quickbazaar

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppEntryPoint(context: Context, navController: NavHostController) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)


    if (currentUser != null) {
        // User is already logged in
        navController.navigate("home") {
            popUpTo("welcome") { inclusive = true }
        }
    }
    else if(isLoggedIn)
    {
        val email=sharedPreferences.getString("email", "abc")
        val password=sharedPreferences.getString("password", "abc")
        val auth=FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email!!, password!!)
    }
    else {
        navController.navigate("welcome")
    }
}
