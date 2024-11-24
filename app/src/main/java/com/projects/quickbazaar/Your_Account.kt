package com.projects.quickbazaar

import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import kotlinx.coroutines.currentCoroutineContext


@Preview
@Composable
fun AccountScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Background color set to white
            .padding(16.dp)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Back Arrow with Circular Outline
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center, // Align the Icon in the center
                modifier = Modifier
                    .size(48.dp) // Size for the circular outline
                    .border(
                        width = 3.dp, // Border thickness
                        color = Color(0xFF000080), // Border color
                        shape = RoundedCornerShape(50) // Circular shape
                    )
                    .clickable { /* Handle back action here */ }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF000080),
                    modifier = Modifier.size(24.dp) // Size of the arrow icon
                )
            }




            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "YOUR ACCOUNT",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0000FF)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Icon
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile",
            modifier = Modifier.size(100.dp),
            tint = Color(0xFF0000FF)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Name and Email
        Text(
            text = "Muhammad Usman",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "myemail@gmail.com",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Options Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OptionItem(icon = Icons.Default.Edit, text = "Update personal info")
            OptionItem(icon = Icons.Default.Lock, text = "Change Password")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Previous Orders
        OptionItem(icon = Icons.Default.Person, text = "Your previous orders")



    }
}

@Composable
fun OptionItem(icon: ImageVector, text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier
                .size(64.dp)
                .background(color = Color(0xFFFFCC00), shape = CircleShape)
                .padding(16.dp),
            tint = Color(0xFF0000FF)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

