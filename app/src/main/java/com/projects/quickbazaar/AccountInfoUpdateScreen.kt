package com.projects.quickbazaar

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.projects.quickbazaar.ui.theme.field_grey
import com.projects.quickbazaar.ui.theme.theme_blue
import com.projects.quickbazaar.ui.theme.theme_orange


@Composable
fun AccountInfoUpdateScreen(
    navController: NavHostController,
    accountUpdateViewModel: AccountUpdateViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNo by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var handledUpdateStatus by remember { mutableStateOf(false) }

    val updateStatus by accountUpdateViewModel.updateStatus.observeAsState()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Back Arrow
            Icon(
                painter = painterResource(id = R.drawable.ic_back), // Replace with your back icon resource
                contentDescription = "Back",
                tint = theme_blue,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(10.dp)
                    .size(45.dp).offset(2.dp, 5.dp)
                    .clickable { navController.popBackStack() }
            )

            Text(
                text = "UPDATE\nPERSONAL INFO.",
                fontSize = 45.sp,
                fontWeight = FontWeight.ExtraBold,
                color = theme_blue,
                textAlign = TextAlign.Left,
                modifier = Modifier.offset(14.dp, -10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Name Field
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

            Spacer(modifier = Modifier.height(8.dp))

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", fontWeight = FontWeight.Bold) },
                placeholder = { Text("Enter your Email Address") },
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

            Spacer(modifier = Modifier.height(8.dp))

            // Phone Number Field
            OutlinedTextField(
                value = phoneNo,
                onValueChange = { phoneNo = it },
                label = { Text("Phone No.", fontWeight = FontWeight.Bold) },
                placeholder = { Text("Enter your phone number") },
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
            Spacer(modifier = Modifier.height(8.dp))


            Spacer(modifier = Modifier.height(300.dp))

            // Update Button
            CustomButton.LongButton(onClick = {
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                if (userId != null) {
                    accountUpdateViewModel.updateUser(userId, name, email, phoneNo, address)
                }

            }
                /* else {
                 Toast.makeText(
                     , // Use the retrieved context
                     "Passwords do not match",
                     Toast.LENGTH_SHORT
                 ).show()
             }*/ ,
                colors = ButtonDefaults.outlinedButtonColors(containerColor= theme_orange),
                border = BorderStroke(3.dp, theme_blue),
                text = "Update")
        }
        }

        // Handle Update Status
        updateStatus?.let {
            if (!handledUpdateStatus) {
                if (it.first) {
                    handledUpdateStatus = true

                    Toast.makeText(context, "Account updated successfully!", Toast.LENGTH_SHORT)
                        .show()
                    navController.popBackStack()
                } else {
                    handledUpdateStatus = true

                    Toast.makeText(context, "Error: ${it.second}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
