package com.projects.quickbazaar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.projects.quickbazaar.ui.theme.theme_blue

@Composable
fun ChangePasswordScreen() {
    // State variables to store text field data
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Top-level container

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
        )
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
        // Back Arrow Row
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_back), // Replace with your back icon resource
//                contentDescription = "Back",
//                tint = theme_blue,
//                modifier = Modifier
//                    .align(Alignment.Start)
//                    .padding(10.dp)
//                    .size(45.dp)
//                    .clickable { navController.navigate(navController.popBackStack()) }
//            )
////            Icon(
////                imageVector = Icons.Default.ArrowBack,
////                contentDescription = "Back",
////                tint = Color(0xFF3F1E94),
////                modifier = Modifier.size(32.dp)
////            )
//            Spacer(modifier = Modifier.width(8.dp))
//        }

        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Text(
            text = "CHANGE PASSWORD",
            color = Color(0xFF3F1E94),
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Current Password TextField
        PasswordInputField(
            label = "Current Password",
            placeholder = "Enter current password",
            value = currentPassword,
            onValueChange = { currentPassword = it }
        )

        Spacer(modifier = Modifier.height(40.dp))

        // New Password TextField
        PasswordInputField(
            label = "New Password",
            placeholder = "Enter new password",
            value = newPassword,
            onValueChange = { newPassword = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password TextField
        PasswordInputField(
            label = "Confirm Password",
            placeholder = "Enter current password",
            value = confirmPassword,
            onValueChange = { confirmPassword = it }
        )

        Spacer(modifier = Modifier.weight(1f))

        // Update Button
        Button(
            onClick = {
                // Handle button click, e.g., validate and update passwords
                println("Current Password: $currentPassword")
                println("New Password: $newPassword")
                println("Confirm Password: $confirmPassword")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFA500),
                contentColor = Color(0xFF3F1E94)
            ),
            border = BorderStroke(3.dp, theme_blue),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(50)
        ) {
            Text(text = "Update", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInputField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            //shape = RoundedCornerShape(8.dp)
            shape = RoundedCornerShape(45),


        )
    }
}
