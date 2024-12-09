package com.projects.quickbazaar

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.projects.quickbazaar.ui.theme.field_grey
import com.projects.quickbazaar.ui.theme.theme_blue
import com.projects.quickbazaar.ui.theme.theme_orange

@Composable
fun ResetPasswordScreen(
    navController:NavHostController,
    resetPasswordViewModel:ResetPasswordViewModel
) {
    val context = LocalContext.current
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
            text = "RESET\nPASSWORD",
            fontSize = 60.sp,
            fontWeight = FontWeight.ExtraBold,
            color = theme_blue,
            textAlign = TextAlign.Left,
            modifier = Modifier.offset(14.dp, -10.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .offset(0.dp, 170.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email = ""
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", fontWeight = FontWeight.Bold) },
            placeholder = { Text("Enter your registered email address") },
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
        CustomButton.LongButton(
            onClick = {
                if (email != "" && isValidEmail(email)) {
                    resetPasswordViewModel.sendResetLink(email) { success, msg ->
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        if (success)
                            navController.popBackStack()
                    }
                } else
                    Toast.makeText(
                        context,
                        "Please enter registered email address",
                        Toast.LENGTH_SHORT
                    ).show()
            },

            colors = ButtonDefaults.outlinedButtonColors(containerColor = theme_orange),
            border = BorderStroke(3.dp, theme_blue),
            text = "Get Password Reset Link"
        )

    }
}
