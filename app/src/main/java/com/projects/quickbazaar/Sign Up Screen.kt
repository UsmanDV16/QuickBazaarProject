package com.projects.quickbazaar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.projects.quickbazaar.ui.theme.field_grey
import com.projects.quickbazaar.ui.theme.theme_blue
import com.projects.quickbazaar.ui.theme.theme_orange

val text_field_spacing:Int=15

@Composable
fun SignUpScreen(navController:NavHostController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone_no by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var re_pass by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = AbsoluteAlignment.Left,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(10.dp)
    ) {
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


        Text(
            text = "SIGN UP",
            fontSize = 60.sp,
            fontWeight = FontWeight.ExtraBold,
            color = theme_blue,
            textAlign = TextAlign.Left,
            modifier = Modifier.offset(14.dp, -10.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize().offset(0.dp, 170.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back Icon



        // Email Text Field
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name", fontWeight = FontWeight.Bold) },
            placeholder = { Text("Enter your full name") },
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

        Spacer(modifier = Modifier.height(text_field_spacing.dp))

        // Password Text Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", fontWeight = FontWeight.Bold) },
            placeholder = { Text("Enter email address") },
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

        Spacer(modifier = Modifier.height(text_field_spacing.dp))

        // Password Text Field
        OutlinedTextField(
            value = phone_no,
            onValueChange = { phone_no = it },
            label = { Text("Phone no.", fontWeight = FontWeight.Bold) },
            placeholder = { Text("Enter your phone number") },
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

        Spacer(modifier = Modifier.height(text_field_spacing.dp))

        // Password Text Field
        OutlinedTextField(
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

        Spacer(modifier = Modifier.height(text_field_spacing.dp))

        // Password Text Field
        OutlinedTextField(
            value = re_pass,
            onValueChange = { re_pass = it },
            label = { Text("Confirm Password", fontWeight = FontWeight.Bold) },
            placeholder = { Text("Re-enter password") },
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

        Spacer(modifier = Modifier.height(30.dp))

        // Sign Up Text
        Row(
            modifier = Modifier.padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Already have an account?", fontSize = 14.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Log in",
                fontSize = 14.sp,
                color = theme_blue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { navController.navigate("login") }
            )
        }

        Spacer(modifier = Modifier.height(100.dp))

        CustomButton.LongButton(onClick = {  },
            colors = ButtonDefaults.outlinedButtonColors(containerColor= theme_orange),
            border = BorderStroke(3.dp, theme_blue),
            text = "Create Account")

    }
}