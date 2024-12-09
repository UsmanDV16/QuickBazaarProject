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
fun TrackOrderScreen(navController: NavHostController, orderId: String, viewModel: TrackOrderViewModel) {
    val orderDetails by viewModel._orderDetails.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchOrderDetails(orderId)
    }

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
            text = "TRACK\nORDER",
            fontSize = 60.sp,
            fontWeight = FontWeight.ExtraBold,
            color = theme_blue,
            textAlign = TextAlign.Left,
            modifier = Modifier.offset(14.dp, -10.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize().offset(0.dp, 190.dp).padding(horizontal =20.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Row() {
            Text(
                text = "Order ID:",
                fontSize = 19.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = "${orderDetails?.orderId ?: "Loading..."}",
            fontSize = 19.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 8.dp)
            )

        }
        Text(
            text = "Order Summary",
            fontSize = 23.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            modifier = Modifier.padding(top = 10.dp)
        )

        orderDetails?.products?.forEach { (productId, quantity) ->
            Text(
                text = "$quantity x ${fetchProductName(productId)}",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = "Current Status",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        val statusText = when (orderDetails?.status) {
            "Received/In-Progress" -> "In preparation/packaging"
            "Dispatched" -> "Departured from Warehouse"
            else -> orderDetails?.status ?: "Loading..."
        }
Row(horizontalArrangement = Arrangement.Center,
    modifier = Modifier.fillMaxWidth()) {
    Text(
        text = statusText,
        fontSize = 16.sp,
        color = if (statusText == "Departured from Warehouse") Color.Red else Color.Black,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Expected Arrival: ${orderDetails?.expectedArrival ?: "Loading..."}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = theme_blue,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

