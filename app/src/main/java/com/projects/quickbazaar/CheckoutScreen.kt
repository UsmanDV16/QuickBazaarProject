package com.projects.quickbazaar

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.projects.quickbazaar.ui.theme.field_grey
import com.projects.quickbazaar.ui.theme.theme_blue
import com.projects.quickbazaar.ui.theme.theme_orange
import kotlin.math.absoluteValue


@Composable
fun CheckoutScreen(
    userId: String,
    subtotal: Int,
    deliveryCharges: Int,
    viewModel: CheckoutViewModel = CheckoutViewModel(),
    navController: NavHostController
) {
    Log.d(subtotal.toString(), subtotal.toString())
    var address by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("Cash") }
    var couponCode by remember { mutableStateOf("") }
    var paymentSuccessful by remember { mutableStateOf(false) } // Track payment success state
    var couponApplied by remember {
        mutableStateOf(false)
    }
    val _context= LocalContext.current
    LaunchedEffect(Unit) {
        if(!viewModel.isLoaded.value||viewModel.subtotal!=subtotal||viewModel.deliveryCharges!=deliveryCharges)
            viewModel.setAmounts(subtotal, deliveryCharges)
    }

    // Handle deep linking
    val context = LocalContext.current
    val deepLinkUri = (context as? Activity)?.intent?.data
    if (deepLinkUri != null && deepLinkUri.host == "payment-complete") {
        // Payment was successful
        paymentSuccessful = true
        // You can now place the order, handle other UI changes, or navigate
        viewModel.placeOrder(userId, paymentMethod, address) {
            // Handle successful order placement
            navController.navigate("orderPlaced")
        }
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
            text = "CHECKOUT",
            fontSize = 60.sp,
            fontWeight = FontWeight.ExtraBold,
            color = theme_blue,
            textAlign = TextAlign.Left,
            modifier = Modifier.offset(14.dp, -10.dp)
        )
    }
    Column(
        modifier=Modifier.fillMaxSize().offset(0.dp, 140.dp).padding(20.dp)

    ) {
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Delivery Address", fontWeight = FontWeight.Bold) },
            placeholder = { Text("Enter your delivery address") },
            modifier = Modifier.fillMaxWidth(1f),
            shape = RoundedCornerShape(45),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedLabelColor = theme_blue,
                unfocusedLabelColor = Color.Gray,
                focusedIndicatorColor = Color.Gray,
                unfocusedIndicatorColor = Color.Gray
            ),
            singleLine = true,
        )
        Spacer(modifier=Modifier.height(15.dp))
        Row(
            horizontalArrangement = Arrangement.Start
        ) {

            // Payable Amount
            Text(
                text = "Payable Amount",
                fontWeight = FontWeight.ExtraBold,
                color = theme_blue,
                fontSize = 23.sp,
                //modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        Spacer(modifier=Modifier.height(10.dp))

        Row(modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween)
        {
            Text(
                text = "Your sub-total: ",
                fontWeight = FontWeight.SemiBold,
                fontSize=16.sp
            )
            Text(
                "Rs. $subtotal",
                fontWeight = FontWeight.SemiBold,
                fontSize=16.sp

            )
        }
        Row(modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween)
        {
            Text(
                text = "Delivery Charges: ",
                fontWeight = FontWeight.SemiBold,
                fontSize=16.sp

            )
            Text(
                "Rs. $deliveryCharges",
                fontWeight = FontWeight.SemiBold,
                fontSize=16.sp

            )
        }
        if(couponApplied)
        {
            Row(modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween)
            {
                Text(
                    text = "Coupon discount: ",
                    fontWeight = FontWeight.SemiBold,
                    fontSize=16.sp

                )
                Text(
                    "-Rs. ${((subtotal+deliveryCharges) * viewModel.discount.value!!) / 100}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize=16.sp

                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween)
        {
            Text(
                text = "TOTAL",
                fontWeight = FontWeight.Bold,
                color=Color.Red,
                fontSize=20.sp
            )
            Text(
                "Rs. ${viewModel.totalAmount.value}",
                fontWeight = FontWeight.Bold,
                color=Color.Red,
                fontSize=19.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        // Payment Options
        Text(
            text = "Payment Options",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 19.sp,
            //modifier = Modifier.padding(vertical = 8.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(field_grey, shape = RoundedCornerShape((20)))

        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = paymentMethod == "Cash",
                    onClick = { paymentMethod = "Cash" }
                )
                Text("Cash on delivery")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = paymentMethod == "Card",
                    onClick = { paymentMethod = "Card" }
                )
                Text("Credit or Debit card")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        if (!couponApplied) {
            OutlinedTextField(
                value = couponCode,
                onValueChange = { couponCode = it },
                label = { Text("Coupon", fontWeight = FontWeight.Bold) },
                placeholder = { Text("Enter coupon code") },
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .width(10.dp),
                shape = RoundedCornerShape(45),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedLabelColor = theme_blue,
                    unfocusedLabelColor = Color.Gray,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.Gray
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.applyCoupon(couponCode) { msg ->
                            Toast.makeText(
                                _context,
                                msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            )
        }
    else{
        Row(modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween)
        {
            Text(couponCode,
                fontSize = 18.sp,
                color=Color.Green

                )
            Button(
                onClick = { couponApplied=false
                          couponCode=""
                          },
                modifier = Modifier
                    .width(50.dp)
                    .height(30.dp)
                ,
                border = BorderStroke(3.dp, theme_blue),
                shape= RoundedCornerShape(15),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = theme_orange)
            ) {
                Text(text = "Remove", color = theme_blue, fontSize = 12.sp)

            }
        }
        }

        Spacer(modifier = Modifier.height(110.dp))
        Row(verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

    Button(
        enabled = address != "",
        onClick = {
            if (paymentMethod == "Cash") {
                viewModel.placeOrder(
                    userId = userId,
                    paymentMethod = "Cash",
                    address = address
                ) {
                    navController.navigate("orderPlaced/${viewModel.orderId}/${viewModel.orderTimeAndDate}")
                }
            } else {
                // Handle Stripe Payment
                val stripePaymentUrl = "https://your-stripe-demo-link.com/payment"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(stripePaymentUrl))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        },
        modifier = Modifier
            .width(120.dp)
            .height(80.dp),
        border = BorderStroke(3.dp, theme_blue),
        shape = RoundedCornerShape(40),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = theme_orange, disabledContainerColor = field_grey,
            disabledContentColor = Color.Gray
        )
    ) {
        Text(
            text = "Place Order",
            color = theme_blue,
            fontSize = 15.sp,
            fontWeight = FontWeight.ExtraBold
        )

    }
    // Place Order Button

}
        // Show message if payment was successful
        if (paymentSuccessful) {
            Text(
                text = "Payment Successful! Your order is being processed.",
                color = Color.Green,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}


/*

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
*/
