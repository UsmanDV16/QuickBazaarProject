package com.projects.quickbazaar

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewArrivalsViewModel:ProductViewModel() {

    init{
        isLoading.value=true
        fetchAllProducts()
    }

    fun fetchAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Fetch all products from Firebase
                val snapshot = database.get().await()
                val productList = mutableListOf<Pair<ProductHighlight, LocalDate>>()

                for (productSnapshot in snapshot.children) {
                    val dateAdded = productSnapshot.child("DateAdded").value?.toString()
                    if (dateAdded != null) {
                        val parsedDate = LocalDate.parse(dateAdded, DateTimeFormatter.ISO_DATE)
                        val product = ProductHighlight(
                            ID=productSnapshot.key.toString(),

                            name = productSnapshot.child("Name").value?.toString() ?: "Unknown",
                            price = productSnapshot.child("Price").value?.toString() ?: "0",
                            imageUrl = productSnapshot.child("Images").child("1").value?.toString() ?: ""
                        )
                        productList.add(product to parsedDate)
                    }
                }

                // Filter products for the current and previous months
                val currentDate = LocalDate.now()
                val currentMonth = currentDate.monthValue
                val currentYear = currentDate.year
                val previousMonth = if (currentMonth == 1) 12 else currentMonth - 1
                val previousYear = if (currentMonth == 1) currentYear - 1 else currentYear

                val currentMonthProducts = productList.filter { (_, date) ->
                    date.monthValue == currentMonth && date.year == currentYear
                }.map { it.first }

                val previousMonthProducts = productList.filter { (_, date) ->
                    date.monthValue == previousMonth && date.year == previousYear
                }.map { it.first }

                // Update products list based on availability
                allProducts.clear()
                if (currentMonthProducts.isNotEmpty()) {
                    allProducts.addAll(currentMonthProducts)
                } else if (previousMonthProducts.isNotEmpty()) {
                    allProducts.addAll(previousMonthProducts)
                }

                allProducts.shuffle() // Shuffle for randomness
                loadMoreProducts() // Load the first 10 products
                isLoading.value = false
            } catch (e: Exception) {
                // Handle errors
                Log.e("NewArrivalsViewModel", e.toString())
                isLoading.value = false
            }
        }
    }
}