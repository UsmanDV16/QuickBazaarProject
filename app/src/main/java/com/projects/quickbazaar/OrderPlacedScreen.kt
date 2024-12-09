package com.projects.quickbazaar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.projects.quickbazaar.ui.theme.theme_blue

@Composable
fun OrderPlacedScreen(navController: NavHostController,
                      timeAndDate:String,
                      orderId:String) {


    Column (
        horizontalAlignment = AbsoluteAlignment.Left,
        verticalArrangement = Arrangement.Top,
        modifier=Modifier.padding(10.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back), // Replace with your back icon resource
            contentDescription = "Back",
            tint = theme_blue,
            modifier = Modifier
                .align(Alignment.Start)
                //.padding(10.dp)
                .size(42.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Checkmark inside a circle
        Box(

            modifier = Modifier
                .size(240.dp)
                .border(4.dp, Color(0xFF000080), CircleShape)
                .background(Color(0xFFFFA500), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            val checkIcon: Painter = painterResource(id = R.drawable.baseline_check_24) // Replace with your image resource
            Image(
                painter = checkIcon,
                contentDescription = "Check",
                modifier = Modifier.size(160.dp) // Adjust size as needed
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Order Information
        Text(
            text = "Order Placed!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = timeAndDate,
            fontSize = 16.sp,
            color = Color.Gray
        )

        Text(
            text = "Order ID: $orderId",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Track Order Button
        Button(
            onClick = { navController.navigate("trackOrder/${orderId}") },
            border = BorderStroke(3.dp, theme_blue),
            colors = ButtonDefaults.buttonColors(Color(0xFFFFA500))
        ) {
            Text(text = "Track Order", color = Color(0xFF000080))
        }
    }
}

