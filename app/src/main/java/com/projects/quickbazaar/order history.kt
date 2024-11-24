import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                OrdersHistoryPage()
            }
        }
    }
}

@Composable
fun OrdersHistoryPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Back Arrow and Title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        )

        {
            Box(
                modifier = Modifier
                    .size(40.dp) // Size of the circular outline
                    .clip(CircleShape) // Clip the box to a circle
                    .border(
                        width = 2.dp, // Border width
                        color = Color(0xFF1E008D), // Circular border color (dark blue)
                        shape = CircleShape // Circular shape for the border
                    )
                    .clickable { /* Handle back action */ },
                contentAlignment = Alignment.Center // Centers the arrow inside the circle
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF1E008D), // Blue color for the arrow icon
                    modifier = Modifier.size(24.dp) // Size of the arrow
                )
            }
            Spacer(modifier = Modifier.width(16.dp))


            Column {
                Text(
                    text = "   ",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E008D)
                )
                Text(
                    text = "   ",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E008D)
                )
                Text(
                    text = "ORDERS",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E008D)
                )
                Text(
                    text = "HISTORY",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E008D)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Order Items
        LazyColumn {
            items(3) { index ->
                OrderItemCard(
                    orderId = "12443782788${index + 1}",
                    itemsDescription = "1 x SoundPulse Pro TWS Bluetooth\n2 x PowerLink Max USB Cable...",
                    amount = if (index == 0) "3200" else "2350",
                    date = "02:31:32, 11/0${8 + index}/24",
                    status = when (index) {
                        0 -> "Track"
                        1 -> "Delivered"
                        else -> "Cancelled"
                    }
                )
            }
        }
    }
}

@Composable
fun OrderItemCard(
    orderId: String,
    itemsDescription: String,
    amount: String,
    date: String,
    status: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp)

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Order ID: $orderId", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = itemsDescription,

                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Amount: Rs. $amount",
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
                Text(
                    text = date,

                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            if (status == "Track") {
                Button(
                    onClick = { /* Handle track action */ },
                    modifier = Modifier
                        .border(
                            width = 3.dp,
                            color = Color(0xFF1E008D),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9800),
                        contentColor = Color(0xFF1E008D)
                    ),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(text = status)
                }
            } else {
                Text(
                    text = status,
                    color = when (status) {
                        "Delivered" -> Color(0xFF388E3C)
                        "Cancelled" -> Color.Red
                        else -> Color.Black
                    },
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrdersHistoryPagePreview() {
    MaterialTheme {
        OrdersHistoryPage()
    }
}
