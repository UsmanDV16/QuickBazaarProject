package com.projects.quickbazaar

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
fun ShoppingCartScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Back arrow with outline
            Icon(
                painter = painterResource(id = R.drawable.ic_back), // Replace with your back icon resource
                contentDescription = "Back",
                tint = theme_blue,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(10.dp)
                    .size(45.dp)
                    .clickable { /*navController.popBackStack()*/ }
            )


            Text(
                text = "SHOPPING CART",
                fontSize = 60.sp,
                fontWeight = FontWeight.ExtraBold,
                color = theme_blue,
                textAlign = TextAlign.Left,
                modifier = Modifier.offset(14.dp, -10.dp)
            )


            // Cart items
            CartItem(
                title = "SoundPulse Pro TWS Bluetooth Earbuds – True Wireless Stereo Earphones with Hi-Fi Audio",
                price = "Rs. 1000",
                imageRes = R.drawable.earbuds
            )

            CartItem(
                title = "PowerLink Max USB Cable – High-Speed Charging and Data Sync Cable with Durable Braided Design",
                price = "Rs. 2000",
                imageRes = R.drawable.usb_cable
            )

            Spacer(modifier = Modifier.weight(1f))

            // Summary and checkout
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "SUBTOTAL",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp // Larger subtotal text
                    )
                    Text(
                        text = "Rs. 3200",
                        fontSize = 28.sp, // Larger subtotal price
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Delivery Charges = Rs. 200",
                        color = Color.Gray,
                        fontSize = 18.sp // Slightly larger text
                    )
                }

                // Checkout button
                Button(
                    onClick = { /* Handle checkout */ },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .height(56.dp)
                        .border(2.dp, Color(0xFF0000FF), RoundedCornerShape(22.dp)), // Blue outline
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFA500) // Orange-yellow color
                    )
                ) {
                    Text(
                        text = "Checkout >",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CartItem(title: String, price: String, imageRes: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .background(
                color = Color.LightGray.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        // Product image
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp) // Larger product image
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.width(12.dp)) // Reduced spacer width

        // Column for product description and price
        Column(
            modifier = Modifier
                .weight(1f) // Ensures the column takes up all available space
                .padding(end = 12.dp) // Padding to avoid clipping on the right
        ) {
            // Product title
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Product price
            Text(
                text = price,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.width(12.dp)) // Spacer for better alignment

        // Quantity controls
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Add button
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color(0xFFFFA500), CircleShape) // Orange-yellow background
                    .border(2.dp, Color(0xFF0000FF), CircleShape) // Blue outline
                    .clickable { /* Increase quantity */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Increase",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }

            Text(
                text = "1",
                fontSize = 20.sp, // Larger font size for quantity
                fontWeight = FontWeight.Bold
            )

            // Remove button
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color(0xFFFFA500), CircleShape) // Orange-yellow background
                    .border(2.dp, Color(0xFF0000FF), CircleShape) // Blue outline
                    .clickable { /* Decrease quantity */ },
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier.size(30.dp)
                ) {
                    drawLine(
                        color = Color.White,
                        start = Offset(0f, size.height / 2),
                        end = Offset(size.width, size.height / 2),
                        strokeWidth = 6f
                    )
                }
            }
        }
    }
}

