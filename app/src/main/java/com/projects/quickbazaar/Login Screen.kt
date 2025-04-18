package com.projects.quickbazaar

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.projects.quickbazaar.ui.theme.field_grey
import com.projects.quickbazaar.ui.theme.theme_blue
import com.projects.quickbazaar.ui.theme.theme_orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController:NavHostController, loginViewModel: LoginViewModel = viewModel())
{
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var handledLoginStatus by remember { mutableStateOf(false) }
    val loginStatus by loginViewModel.loginStatus.observeAsState()
    val context= LocalContext.current

    Column (
        horizontalAlignment = AbsoluteAlignment.Left,
        verticalArrangement = Arrangement.Top,
        modifier=Modifier.padding(10.dp)
    ){
        Icon(
            painter = painterResource(id = R.drawable.ic_back), // Replace with your back icon resource
            contentDescription = "Back",
            tint = theme_blue,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(10.dp)
                .size(45.dp)
                .clickable { navController.popBackStack() }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "LOGIN",
            fontSize = 60.sp,
            fontWeight = FontWeight.ExtraBold,
            color = theme_blue,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(20.dp)
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back Icon


        Spacer(modifier = Modifier.height(60.dp))

        // Email Text Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Enter Email", fontWeight = FontWeight.Bold) },
            placeholder = { Text("Enter email") },
            modifier = Modifier.fillMaxWidth(0.9f),
            shape = RoundedCornerShape(45),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = field_grey,
                unfocusedContainerColor = Color.Transparent,
                focusedLabelColor = theme_blue,
                unfocusedLabelColor = Color.Gray,
                focusedIndicatorColor = Color.Gray,
                unfocusedIndicatorColor = Color.Gray
            ),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Password Text Field
        OutlinedTextField(
            visualTransformation =PasswordVisualTransformation(),
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", fontWeight = FontWeight.Bold) },
            placeholder = { Text("Enter password") },
            modifier = Modifier.fillMaxWidth(0.9f),
            shape = RoundedCornerShape(45),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedLabelColor = theme_blue,
                unfocusedLabelColor = Color.Gray,
                focusedIndicatorColor = Color.Gray,
                unfocusedIndicatorColor = Color.Gray
            ),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Forgot Password
        Text(
            text = "Forgot Password?",
            color = theme_blue,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 32.dp)
                .clickable { navController.navigate("resetPassword") }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Log In Button
        CustomButton.LongButton(onClick = {
            if(isValidEmail(email))
                loginViewModel.login(email, password)
            else
                Toast.makeText(
                    context,
                    // Use the retrieved context
                    "Email format not correct",
                    Toast.LENGTH_SHORT
                ).show() },
            colors = ButtonDefaults.outlinedButtonColors(containerColor= theme_orange),
            border = BorderStroke(3.dp, theme_blue),
            text = "Log in")

        Spacer(modifier = Modifier.height(16.dp))

        // Sign Up Text
        Row(
            modifier = Modifier.padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Don’t have an account?", fontSize = 14.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Sign up",
                fontSize = 14.sp,
                color = theme_blue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { navController.navigate("signup") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
/*
        // OR Text
        Text(
            text = "OR",
            color = Color.Gray,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))*/

        // Google Sign-In Button
        /*Button(
            onClick = { *//* Handle Google sign-in *//* },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            border = BorderStroke(2.dp, Color.Gray),
            modifier = Modifier.size(50.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_google), // Replace with your Google icon resource
                contentDescription = "Google Sign-In",
                modifier = Modifier.fillMaxSize()
            )
        }*/
    }

    loginStatus?.let {
        if (!handledLoginStatus) {
            if (it.first) {
                handledLoginStatus=true
                val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLoggedIn", true)
                editor.putString("email", email)
                editor.putString("password", password)
                editor.apply()
                Toast.makeText(LocalContext.current, "Logged-in Successfully!", Toast.LENGTH_SHORT)
                    .show()
                navController.navigate("home")
                Log.d("eloo", "Hello")
            } else {
                handledLoginStatus=true
                Toast.makeText(LocalContext.current, "Error: ${it.second}", Toast.LENGTH_SHORT)
                    .show()
                Log.d(it.second, "k")
            }

        }
    }
}