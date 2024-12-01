import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.projects.quickbazaar.Order
import com.projects.quickbazaar.OrderHistoryViewModel
import com.projects.quickbazaar.R
import com.projects.quickbazaar.ui.theme.field_grey
import com.projects.quickbazaar.ui.theme.theme_blue
import com.projects.quickbazaar.ui.theme.theme_orange


@Composable
fun OrderHistoryScreen(navController: NavHostController, userId: String) {
    val viewModel: OrderHistoryViewModel = viewModel()
    val orders by viewModel.orderList.observeAsState(emptyList())
    LaunchedEffect(Unit) {
        viewModel.fetchOrders(userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back button
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
            text = "ORDERS HISTORY",
            fontSize = 60.sp,
            fontWeight = FontWeight.ExtraBold,
            color = theme_blue,
            textAlign = TextAlign.Left,
            modifier = Modifier.offset(14.dp, -10.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Orders List in Grey Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .background(field_grey, shape = RoundedCornerShape(16.dp))
                .padding(8.dp)
        ) {
            LazyColumn {
                items(orders) { order ->
                    OrderItem(navController, order)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun OrderItem(navController: NavHostController, order: Order) {
    val statusColor = when (order.status) {
        "Cancelled" -> Color.Red
        "Delivered" -> Color.Black
        else -> theme_blue
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        // Order ID and Track Button
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Order ID: ${order.orderId}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            if (order.status == "Received/In-Progress") {
                Row {
                    Button(
                        onClick = {navController.navigate(("trackOrder/${order.orderId}")) },
                        colors = ButtonDefaults.buttonColors(containerColor = theme_orange),
                        modifier = Modifier.defaultMinSize(minHeight = 32.dp, minWidth = 70.dp)
                    ) {
                        Text(text = "Track", fontSize = 12.sp, color = theme_blue)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { /* Cancel Order Logic */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        border = BorderStroke(1.dp, theme_blue),
                        modifier = Modifier.defaultMinSize(minHeight = 32.dp, minWidth = 70.dp)
                    ) {
                        Text(text = "Cancel", fontSize = 12.sp, color = theme_blue)
                    }
                }
            } else if (order.status == "Dispatched") {
                Button(
                    onClick = { navController.navigate("trackOrder/${order.orderId}") },
                    colors = ButtonDefaults.buttonColors(containerColor = theme_orange),
                    modifier = Modifier.defaultMinSize(minHeight = 32.dp, minWidth = 70.dp)
                ) {
                    Text(text = "Track", fontSize = 12.sp, color = theme_blue)
                }
            } else {
                Text(
                    text = order.status,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = statusColor
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Products Preview
        val productPreview = order.products.entries.joinToString(", ") { entry ->
            "${entry.value}x ${entry.key.take(15)}..."
        }
        Text(
            text = productPreview,
            fontSize = 14.sp,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Amount and Time
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Amount: Rs. ${order.products.values.sum()}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
            Text(
                text = order.timeDate,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
