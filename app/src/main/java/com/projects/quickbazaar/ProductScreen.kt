package com.projects.quickbazaar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.projects.quickbazaar.ui.theme.theme_blue
import com.projects.quickbazaar.ui.theme.theme_orange

@Composable
fun ProductImageSlider(images: List<String>) {
    val pagerState = rememberPagerState(initialPage = 0){images.size}

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        // Horizontal Pager for images
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            AsyncImage(model = images[page],
                contentDescription = "Product Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        // Indicator Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(images.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (pagerState.currentPage == index) theme_orange else Color.Gray)
                )
            }
        }
    }
}


@Composable
fun ProductScreen(
    navController: NavHostController,
    productId: String,
    productViewModel: ProductDetailViewModel = viewModel()
) {
    // Observe data from the ViewModel
    val product by productViewModel.product.collectAsState()
    val reviews by productViewModel.reviews.collectAsState()
    val exploreProducts by productViewModel.exploreProducts.collectAsState()
    val isAddedToCart by productViewModel.isAddedToCart.collectAsState()

    LaunchedEffect(productId) {
        productViewModel.loadProduct(productId)
    }

    Column {
        // Back Button
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back",
                tint = theme_blue,
                modifier = Modifier
                    .size(45.dp)
                    .clickable { navController.popBackStack() }
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Product Images
            item {
                ProductImageSlider(images = product.images)
            }

            // Product Details
            item {
                Text(
                    text = product.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = theme_blue
                )
                Text(
                    text = "⭐ ${product.rating} (${product.totalReviews} Reviews)",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Rs. ${product.price}",
                    fontSize = 18.sp,
                    color = theme_orange,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = product.description,
                    fontSize = 14.sp,
                    color = theme_blue,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Reviews Section
            item {
                Text(
                    text = "Reviews",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = theme_blue,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            items(reviews) { review ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = review.username,
                            fontWeight = FontWeight.Bold,
                            color = theme_blue
                        )
                        Text(
                            text = "⭐ ${review.rating}",
                            color = theme_orange
                        )
                        Text(
                            text = review.text,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Explore Products Section
            item {
                Text(
                    text = "- Explore -",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = theme_blue,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            items(exploreProducts.chunked(2)) { row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    row.forEach { product ->
                        ProductCard(navController, product = product, modifier = Modifier.weight(1f))
                    }
                }
            }

            // Add to Cart Button
            item {
                Button(
                    onClick = {
                        productViewModel.addToCart(product.id)
                    },
                    enabled = !isAddedToCart,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = theme_orange,
                        disabledContainerColor = Color.Gray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = if (isAddedToCart) "Added to Cart" else "Add to Cart",
                        color = Color.White
                    )
                }
            }
        }
    }
}
