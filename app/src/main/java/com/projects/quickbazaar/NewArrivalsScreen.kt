package com.projects.quickbazaar

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.projects.quickbazaar.ui.theme.theme_blue
import com.projects.quickbazaar.ui.theme.theme_orange


@Composable
fun NewArrivalsScreen(navController: NavHostController, newArrivalsViewModel:NewArrivalsViewModel)
{
    val products = newArrivalsViewModel.products
    val isLoading = newArrivalsViewModel.isLoading
    val showLoadMore = remember { mutableStateOf(true) }
    Log.d("Navigationout", "Current Back Stack:")
    if (isLoading.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = theme_orange)
        }
    } else {
        Log.d("Navigationin", "Current Back Stack:")

        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(10.dp)
        ) {
            Log.d("Navigationinrow", "Current Back Stack:")

            Icon(
                painter = painterResource(id = R.drawable.ic_back), // Replace with your back icon resource
                contentDescription = "Back",
                tint = theme_blue,
                modifier = Modifier
                    .padding(10.dp)
                    .size(45.dp)
                    .clickable { navController.popBackStack()
                        Log.d("Navigation", "Back button pressed")}
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                Text(
                    text = "NEW ARRIVALS",
                    fontSize = 60.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = theme_blue,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.offset(14.dp, 90.dp)
                )

            }
            item {
                Spacer(modifier =Modifier.height(80.dp))
            }
            items(products.chunked(2)) { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowItems.forEach { product ->
                        ProductCard(navController, product = product, modifier = Modifier.weight(1f))
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
                                newArrivalsViewModel.loadMoreProducts()
                                if (newArrivalsViewModel.allProducts.isEmpty()) showLoadMore.value =
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