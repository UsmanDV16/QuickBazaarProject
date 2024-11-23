package com.projects.quickbazaar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.projects.quickbazaar.ui.theme.theme_blue


@Composable
fun CheckoutScreen() {
    // State variables to store input and selections
    var address by remember { mutableStateOf("") }
    var couponCode by remember { mutableStateOf("") }
    var paymentOption by remember { mutableStateOf("Cash on delivery") }

        // Back arrow and title

        Column (
            horizontalAlignment = AbsoluteAlignment.Left,
            verticalArrangement = Arrangement.Top,
            modifier=Modifier.padding(10.dp)
        ){
            Icon(
                painter = painterResource(id = R.drawable.ic_back), // Replace with your back icon resource
                contentDescription = "Back",
                tint = theme_blue,
                modifier = Modifier
                    .align(Alignment.Start)
                    //.padding(10.dp)
                    .size(42.dp)
            )
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Icon(
//                imageVector = Icons.Default.ArrowBack,
//                contentDescription = "Back",
//                tint = Color(0xFF3F1E94),
//                modifier = Modifier.size(32.dp)
//            )
//            Spacer(modifier = Modifier.width(8.dp))
//        }

        //Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "CHECKOUT",
            color = Color(0xFF3F1E94),
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Address TextField
        AddressInputField(
            label = "Your address",
            placeholder = "Address",
            value = address,
            onValueChange = { address = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Payable Amount Section
        Text(
            text = "Payable Amount",
            color = Color(0xFF3F1E94),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Your sub-total:", color = Color.Black, fontSize = 16.sp)
                Text(text = "Rs. 3000", color = Color.Black, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Delivery charges:", color = Color.Black, fontSize = 16.sp)
                Text(text = "Rs. 200", color = Color.Black, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "TOTAL", color = Color.Red, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = "Rs. 3200", color = Color.Red, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Payment Options
        Text(
            text = "Payment Options",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Payment options radio buttons
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            PaymentOptionRadioButton(
                option = "Cash on delivery",
                selectedOption = paymentOption,
                onOptionSelected = { paymentOption = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            PaymentOptionRadioButton(
                option = "Credit or Debit card",
                selectedOption = paymentOption,
                onOptionSelected = { paymentOption = it }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Coupon Input Field
        AddressInputField(
            label = "Add Coupon",
            placeholder = "Enter coupon code",
            value = couponCode,
            onValueChange = { couponCode = it }
        )

        Spacer(modifier = Modifier.weight(1f))

        // Place Order Button
        Button(
            onClick = {
                // Handle place order logic
                println("Address: $address")
                println("Payment Option: $paymentOption")
                println("Coupon Code: $couponCode")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFA500),
                contentColor = Color(0xFF3F1E94)
            ),
            border = BorderStroke(3.dp, theme_blue),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(50)
        ) {
            Text(text = "Place Order", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressInputField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp)
        )
    }
}

@Composable
fun PaymentOptionRadioButton(
    option: String,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOptionSelected(option) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = option == selectedOption,
            onClick = { onOptionSelected(option) },
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF3F1E94),
                unselectedColor = Color.Black
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = option, color = Color.Black, fontSize = 16.sp)
    }
}
