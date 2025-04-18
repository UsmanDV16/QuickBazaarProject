package com.projects.quickbazaar

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.compose.rememberAsyncImagePainter


@Composable
fun ProductCard(navController: NavHostController, product: ProductHighlight, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(color = Color.Transparent, shape = RoundedCornerShape(20.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image with border
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(20.dp))
                .clickable { navController.navigate("productDetails/${product.ID}") }
        ) {

            AsyncImage(
                model=product.imageUrl,
                contentDescription = "Product Image",
                modifier = Modifier.fillMaxSize(),
                placeholder = painterResource(id = R.drawable.home_icon),
                error = painterResource(id = R.drawable.category_icon)
            )

        }

        Spacer(modifier = Modifier.height(8.dp))

        // Product name
        Text(
            text = product.name,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        // Product price
        Text(
            text = "Rs. ${product.price}",
            fontSize = 18.sp,
            color = Color.Red,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )
    }
}

