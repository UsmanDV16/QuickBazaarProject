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
    var handled=false

    if (currentUser != null&&!handled) {
        // User is already logged in
        handled=true
        navController.navigate("home")
    }
    else if(isLoggedIn&&!handled)
    {
        handled=true

        val email=sharedPreferences.getString("email", "abc")
        val password=sharedPreferences.getString("password", "abc")
        val auth=FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email!!, password!!)
        navController.navigate("home")
    }
    else if(!handled) {
        handled=true
        navController.navigate("welcome")
    }
}
