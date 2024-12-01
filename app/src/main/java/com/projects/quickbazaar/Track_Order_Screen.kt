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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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


@Composable
fun TrackOrderScreen(navController: NavHostController, orderId: String) {
    val viewModel: TrackOrderViewModel = viewModel()
    val orderDetails by viewModel.orderDetails.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchOrderDetails(orderId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back button
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back",
            tint = theme_blue,
            modifier = Modifier
                .align(Alignment.Start)
                .size(45.dp)
                .clickable { navController.popBackStack() }
        )

        // Title
        Text(
            text = "TRACK ORDER",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = theme_blue,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Scrollable content
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            orderDetails?.let { order ->
                item {
                    // Order ID
                    Text(
                        text = "Order ID: ${order.orderId}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Order Summary
                    Text(
                        text = "Order Summary",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    order.products.forEach { (productId, quantity) ->
                        Text(
                            text = "$quantity x $productId",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Current Status
                    Text(
                        text = "Current Status",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    val statusText = when (order.status) {
                        "Received", "In Progress" -> "In preparation/packaging"
                        "Dispatched" -> "Departured from Warehouse"
                        else -> order.status
                    }

                    Text(
                        text = statusText,
                        fontSize = 16.sp,
                        color = if (statusText == "Departured from Warehouse") Color.Red else Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Expected Arrival
                    Text(
                        text = "Expected Arrival: ${order.expectedArrival}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme_blue,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

/*
fun TrackOrderScreen() {
    Scaffold(
        containerColor = Color.White
    ) { contentPadding -> // Accept the content padding from Scaffold
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding) // Apply content padding here
                .padding(16.dp) // Additional padding for screen content
        ) {
            // Back Arrow with Circular Outline
            Box(
                modifier = Modifier
                    .size(56.dp) // Slightly larger size
                    .clip(CircleShape)
                    .border(4.dp, Color.Blue, CircleShape) // Thicker blue outline
                    .clickable { */
/* Handle back click *//*
 },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp) // Increased icon size
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Row for Title and Icons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp), // Padding for alignment
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Track Order Title
                Text(
                    text = "TRACK\nORDER",
                    color = Color(0xFF000080), // Navy blue color
                    fontSize = 49.sp, // Larger font size
                    fontWeight = FontWeight.Bold,
                    lineHeight = 44.sp, // Increased line height
                    modifier = Modifier.weight(1f) // Take up available space
                )

                // Order Tracking Icons
                Row(
                    modifier = Modifier.weight(1f), // Align icons to the right
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color.Black,
                        modifier = Modifier.size(40.dp) // Increased icon size
                    )
                    Icon(
                        imageVector = Icons.Default.LocalShipping, // Valid shipping icon
                        contentDescription = "Shipping",
                        tint = Color.Black,
                        modifier = Modifier.size(40.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Delivered",
                        tint = Color(0xFF00FF00), // Green color
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            // Line with Dots Under Icons
            Spacer(modifier = Modifier.height(8.dp)) // Reduced spacing
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 100.dp) // Adjust line width to fit under icons
                    .offset(x = 84.dp), // Shift the line and dots to the right
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Dot 1
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
                // Line between Dot 1 and Dot 2
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                        .background(Color.Gray)
                )
                // Dot 2
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
                // Line between Dot 2 and Dot 3
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                        .background(Color.Gray)
                )
                // Dot 3
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Order ID
            Text(
                text = "Order ID: 12945378840920",
                fontSize = 20.sp, // Larger font size
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Order Summary Title
            Text(
                text = "Order Summary",
                fontSize = 22.sp, // Larger font size
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Order Summary Details
            Text(
                text = "1 x SoundPulse Pro TWS Bluetooth Earbuds – True Wireless Stereo Earphones with Hi-Fi Audio",
                fontSize = 18.sp, // Larger font size
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "1 x PowerLink Max USB Cable – High-Speed Charging and Data Sync Cable with Durable Braided Design",
                fontSize = 18.sp, // Larger font size
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Current Status Title
            Text(
                text = "Current Status",
                fontSize = 22.sp, // Larger font size
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Current Status Text
            Text(
                text = "Departed from Warehouse",
                fontSize = 20.sp, // Larger font size
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Expected Arrival
            Text(
                text = "Expected Arrival: 12/12/24",
                fontSize = 20.sp, // Larger font size
                fontWeight = FontWeight.Bold,
                color = Color(0xFF000080) // Navy blue color
            )
        }
    }
}
*/
