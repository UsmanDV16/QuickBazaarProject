package com.projects.quickbazaar

import android.os.SystemClock.sleep
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.projects.quickbazaar.ui.theme.field_grey
import com.projects.quickbazaar.ui.theme.theme_blue
import com.projects.quickbazaar.ui.theme.theme_orange
import java.util.concurrent.locks.ReentrantLock


/*@Composable
fun HomeScreen(navController: NavHostController) {
    val getProduct = GetProductHighlightFromDB()
    val fetchedProducts=getProduct.getAllProducts()
    val products = remember { mutableStateListOf<ProductHighlight>() }
    val allProducts = remember { mutableStateListOf<ProductHighlight>() }
    val showLoadMore = remember { mutableStateOf(true) }
    val context = LocalContext.current

    // Fetch all products from the database and shuffle them

        // Fetch all products asynchronously


    if (fetchedProducts.isNotEmpty()) {
        allProducts.addAll(fetchedProducts.shuffled()) // Shuffle for randomness
        loadMoreProducts(products, allProducts)
    } else {
        Log.d("hi", "hi")
        Toast.makeText(context, "No products available!", Toast.LENGTH_LONG).show()
        showLoadMore.value = false
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome Header
        item {
            Text(
                text = "Welcome, User!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = theme_orange,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Top Buttons
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    onClick = { *//* Navigate to New Arrivals *//* },
                    colors = ButtonDefaults.buttonColors(containerColor = theme_orange),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "New Arrivals", color = Color.White)
                }

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedButton(
                    onClick = { *//* Navigate to Track Order *//* },
                    colors = ButtonDefaults.buttonColors(containerColor = theme_orange),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Track your order", color = Color.White)
                }
            }
        }

        // Explore Products Title
        item {
            Text(
                text = "Explore products",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E2E2E),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Product Grid
        items(products.chunked(2)) { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach { product ->
                    ProductCard(product = product, modifier = Modifier.weight(1f))
                }
                if (rowItems.size < 2) Spacer(modifier = Modifier.weight(1f))
            }
        }

        // Load More Section
        if (showLoadMore.value) {
            item {
                Text(
                    text = "Load More",
                    fontSize = 16.sp,
                    color = theme_blue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            loadMoreProducts(products, allProducts)
                            if (allProducts.isEmpty()) showLoadMore.value = false
                        }
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            item {
                Text(
                    text = "Looks like it's the end.",
                    fontSize = 16.sp,
                    color = theme_orange,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )


            }
        }
    }
}*/

@Composable
fun HomeScreen(navController: NavHostController, homeViewModel: HomeViewModel = viewModel()) {
    val products = homeViewModel.products
    val isLoading = homeViewModel.isLoading
    val showLoadMore = remember { mutableStateOf(true) }

    if (isLoading.value) {
        // Show loading indicator while data is fetched
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = theme_orange)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Welcome Header
            item {
                Text(
                    text = "Welcome, User!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = theme_orange,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Top Buttons
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedButton(
                        onClick = { navController.navigate("newArrivals") },
                        colors = ButtonDefaults.buttonColors(containerColor = theme_orange),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "New Arrivals", color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    OutlinedButton(
                        onClick = { /* Navigate to Track Order */ },
                        colors = ButtonDefaults.buttonColors(containerColor = theme_orange),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Track your order", color = Color.White)
                    }
                }
            }

            // Explore Products Title
            item {
                Text(
                    text = "Explore products",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E2E2E),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Product Grid
            items(products.chunked(2)) { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowItems.forEach { product ->
                        ProductCard(product = product, modifier = Modifier.weight(1f))
                    }
                    if (rowItems.size < 2) Spacer(modifier = Modifier.weight(1f))
                }
            }

            // Load More Section
            if (showLoadMore.value) {
                item {
                    Text(
                        text = "Load More",
                        fontSize = 16.sp,
                        color = theme_blue,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                homeViewModel.loadMoreProducts()
                                if (homeViewModel.allProducts.isEmpty()) showLoadMore.value = false
                            }
                            .padding(vertical = 16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                item {
                    Text(
                        text = "Looks like it's the end.",
                        fontSize = 16.sp,
                        color = theme_orange,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


/*
fun loadMoreProducts(
    displayedProducts: MutableList<ProductHighlight>,
    remainingProducts: MutableList<ProductHighlight>
) {
    val batchSize = 10
    val nextBatch = remainingProducts.take(batchSize)
    displayedProducts.addAll(nextBatch)
    remainingProducts.removeAll(nextBatch)
}
*/


