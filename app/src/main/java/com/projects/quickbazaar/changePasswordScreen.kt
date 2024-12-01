package com.projects.quickbazaar

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.projects.quickbazaar.ui.theme.field_grey
import com.projects.quickbazaar.ui.theme.theme_blue
import com.projects.quickbazaar.ui.theme.theme_orange


@Composable
fun ChangePasswordScreen(navController:NavHostController, changePassViewModel:ChangePasswordViewModel) {
    val changePasswordStatus by changePassViewModel.changePasswordStatus.observeAsState()
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var handledStatus by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(10.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back), // Replace with your back icon resource
            contentDescription = "Back",
            tint = theme_blue,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(10.dp).offset(2.dp, 5.dp)
                .size(45.dp)
                .clickable { navController.popBackStack() }
        )


        Text(
            text = "CURRENT PASSWORD",
            fontSize = 60.sp,
            fontWeight = FontWeight.ExtraBold,
            color = theme_blue,
            textAlign = TextAlign.Left,
            modifier = Modifier.offset(14.dp, -10.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = currentPassword,
            onValueChange = { currentPassword = it },
            label = { Text("Current Password", fontWeight = FontWeight.Bold) },
            placeholder = { Text("Enter your current password") },
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

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("New Password", fontWeight = FontWeight.Bold) },
            placeholder = { Text("Enter a new password") },
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

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password", fontWeight = FontWeight.Bold) },
            placeholder = { Text("Re-enter new password") },
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

        Spacer(modifier = Modifier.weight(1f))

        CustomButton.LongButton(
            onClick = {
                changePassViewModel.changePassword(currentPassword, newPassword, confirmPassword)
            },
            colors = ButtonDefaults.outlinedButtonColors(containerColor = theme_orange),
            border = BorderStroke(3.dp, theme_blue),
            text = "Update"
        )
    }

    changePasswordStatus?.let {
        if (!handledStatus) {
            if (it.first) {
                Toast.makeText(LocalContext.current, "Password updated successfully!", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            } else {
                Toast.makeText(LocalContext.current, "Error: ${it.second}", Toast.LENGTH_SHORT).show()
            }
            handledStatus = true
        }
    }
}
