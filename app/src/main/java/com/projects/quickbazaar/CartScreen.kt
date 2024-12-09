package com.projects.quickbazaar

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.projects.quickbazaar.ui.theme.field_grey
import com.projects.quickbazaar.ui.theme.theme_blue
import com.projects.quickbazaar.ui.theme.theme_orange
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun CartScreen(
    userId: String,
    navController:NavHostController,
    cartViewModel: CartViewModel = viewModel()
) {
    val cartItems = cartViewModel._cartItems
    val subtotal = cartViewModel._subtotal
    val deliveryCharges = cartViewModel._deliveryCharges

    LaunchedEffect(Unit) {
        if(cartItems.isEmpty()&&!cartViewModel.isLoading.value)
            cartViewModel.fetchCart(userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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
            text = "SHOPPING\nCART",
            fontSize = 60.sp,
            fontWeight = FontWeight.ExtraBold,
            color = theme_blue,
            textAlign = TextAlign.Left,
            modifier = Modifier.offset(14.dp, -10.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)//.border(1.dp, Color.LightGray, RoundedCornerShape(20))
                .background(field_grey, shape = RoundedCornerShape(25.dp))

                .padding(8.dp),
        ) {

            LazyColumn() {
                items(cartItems) { cartItem ->
                    CartItemRow(
                        cartItem = cartItem,
                        onIncrement = {
                            cartViewModel.updateQuantity(userId, cartItem.productId, ++cartItem.quantity.intValue, true)
                        },
                        onDecrement = {
                            cartViewModel.updateQuantity(userId, cartItem.productId, --cartItem.quantity.intValue, false)
                        }
                    )
                    Divider(
                        color = Color.Gray, // Set your desired color
                        thickness = 1.dp,   // Set the thickness of the line
                        modifier = Modifier.fillMaxWidth() // Optional padding
                    )
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        )
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text(
                    text = "SUBTOTAL",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Red,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Rs. ${subtotal.intValue}",
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center

                )
                Text(
                    text = "Delivery Charges = Rs. ${deliveryCharges.intValue}",
                    fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center

                )
            }
            Column(verticalArrangement = Arrangement.Center) {

                Button(
                    enabled = subtotal.intValue!=0,
                    onClick = {
                        Log.d(subtotal.toString(), deliveryCharges.toString())
                        navController.navigate("checkout/${userId}/${subtotal.value}/${deliveryCharges.value}") },

                    modifier = Modifier
                        .width(120.dp)
                        .height(80.dp)
                        ,
                    border = BorderStroke(3.dp, theme_blue),
                    shape= RoundedCornerShape(25),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = theme_orange, disabledContainerColor = field_grey,
                        disabledContentColor = Color.Gray)
                ) {
                    Text(text = "Checkout >", color = theme_blue, fontSize = 18.sp)

                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    cartItem: CartItem,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Transparent)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Product Image
        AsyncImage(
            model=cartItem.imageUrl,
            contentDescription = cartItem.name,
            modifier = Modifier
                .size(64.dp)
                .padding(end = 8.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = cartItem.name, maxLines = 2, overflow = TextOverflow.Ellipsis)

        }

        Column(horizontalAlignment=Alignment.CenterHorizontally) {
            IconButton(onClick = onIncrement
            ) {
                Icon(painter= painterResource(id = R.drawable.increment), contentDescription = "Decrement",
                    modifier=Modifier.size(20.dp), tint=Color.Unspecified)
            }
            Spacer(modifier = Modifier.height(3.dp))
            Text(text = cartItem.quantity.intValue.toString(), fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(3.dp))
            
            IconButton(onClick = onDecrement) {
                Icon(painterResource(id = R.drawable.decrement), contentDescription = "Increment",
                    modifier=Modifier.size(20.dp), tint=Color.Unspecified)
            }
            Spacer(modifier = Modifier.height(2.dp))

            Text(text = "Rs. ${cartItem.price}", color = Color.Red)
        }

    }
}
