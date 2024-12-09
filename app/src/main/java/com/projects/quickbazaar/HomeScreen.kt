package com.projects.quickbazaar

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.projects.quickbazaar.ui.theme.theme_blue
import com.projects.quickbazaar.ui.theme.theme_orange


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
fun HomeScreen(
    navController: NavHostController,
    searchingMode: Boolean,
    searchMode: Boolean,
    searchQuery: String,
    homeViewModel: HomeViewModel
) {


    if (homeViewModel.allProducts.isEmpty() && homeViewModel.products.isEmpty()&&!homeViewModel.isLoading.value)
        homeViewModel.fetchAllProducts()
    if(!homeViewModel.fetched)
        getCurrentUserId()?.let { homeViewModel.fetchLatestOrder(it) }
    var orderId by homeViewModel._latestOrderId
    if (searchMode) {
        // Show search results based on the query
        var searchResults = emptyList<ProductHighlight>()
        if (searchResults.isEmpty()) {
           searchResults= homeViewModel.searchProducts(searchQuery) // Assume a search function exists in the ViewModel
        }
        if(searchResults.isEmpty()){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No results found.", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(searchResults) { product ->
                    ProductCard(navController, product = product)
                }
            }
        }
    } else if (searchingMode) {
        Box(
            modifier = Modifier.fillMaxSize(),
        )

    } else {
        // Default home screen content

        val showLoadMore = remember {
            mutableStateOf(true)
        }
        val products = homeViewModel.products

        val isLoading = homeViewModel.isLoading
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
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item {
                    Spacer(modifier=Modifier.height(32.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { navController.navigate("newArrivals") },
                            colors = ButtonDefaults.buttonColors(containerColor = theme_orange),
                            shape = RoundedCornerShape(25),
                            modifier = Modifier.height(105.dp),
                            border = BorderStroke(2.dp, theme_blue)
                        ) {
                            Text(text = "New Arrivals", color = theme_blue, fontWeight = FontWeight.ExtraBold,
                                fontSize=16.sp)
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(
                            onClick = { navController.navigate("trackOrder/${orderId}") },
                            colors = ButtonDefaults.buttonColors(containerColor = theme_orange),
                            shape = RoundedCornerShape(25),
                            modifier = Modifier.height(105.dp),
                            border = BorderStroke(2.dp, theme_blue)
                        ) {
                            Text(text = "Track Order", color = theme_blue, fontWeight = FontWeight.ExtraBold,
                                fontSize=16.sp)
                        }
                    }
                }

                // Explore Products Title
                item {
                    Spacer(Modifier.height(35.dp))
                    Text(
                        text = "Explore products",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = theme_blue,
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
                            ProductCard(
                                navController,
                                product = product,
                                modifier = Modifier.weight(1f)
                            )
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
                                    if (homeViewModel.allProducts.size==homeViewModel.displayed) showLoadMore.value =
                                        false
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
}


