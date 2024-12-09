package com.projects.quickbazaar

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.quickbazaar.ui.theme.theme_blue
import com.projects.quickbazaar.ui.theme.theme_light_orange
import com.projects.quickbazaar.ui.theme.theme_orange
import java.util.concurrent.locks.ReentrantLock

@Composable
fun CategoriesScreen(navController: NavHostController, categoryViewModel: CategoryViewModel = viewModel()) {
    val categories by categoryViewModel.categories.collectAsState()
    val isLoading = categoryViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        if(categoryViewModel.categories.value.isEmpty()&&categoryViewModel.isLoading.value)
            categoryViewModel.fetchCategories()
    }

    if (isLoading.value) {
        // Show loading indicator
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
                .padding(16.dp)
                .background(theme_light_orange)
        ) {
            // Categories Header
            item {
                Text(
                    text = "Categories",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = theme_blue,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Display Main Categories and Subcategories
            categories.forEach { mainCategory ->
                item {
                    Column(modifier = Modifier.padding(bottom = 16.dp)) {
                        // Main Category Title
                        Text(
                            text = mainCategory.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF000000),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        // Subcategories
                        mainCategory.subcategories.forEach { subCategory ->
                            Text(
                                text = "${subCategory.name}  >",
                                fontSize = 16.sp,
                                color = theme_blue,
                                modifier = Modifier
                                    .clickable {
                                        // Navigate to the subcategory screen or perform an action
                                        navController.navigate("categoryProducts/${subCategory.id}/${subCategory.name}")
                                    }
                                    .padding(start = 16.dp, bottom = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}