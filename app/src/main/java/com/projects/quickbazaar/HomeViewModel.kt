package com.projects.quickbazaar

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeViewModel : ProductViewModel() {

    init{
        fetchAllProducts()
    }

    fun searchProducts(query: String): List<ProductHighlight> {
        val tokens = query.trim().split("\\s+".toRegex())
        return allProducts.filter { product ->
            tokens.any { token ->
                product.name.contains(token, ignoreCase = true)
            }
        }
    }
    override fun fetchAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Fetch products from Firebase
                val snapshot = database.get().await()
                val productList = mutableListOf<ProductHighlight>()

                for (childSnapshot in snapshot.children) {
                    val product = ProductHighlight(
                        ID=childSnapshot.key.toString(),

                        name = childSnapshot.child("Name").value?.toString() ?: "Unknown",
                        price = childSnapshot.child("Price").value?.toString() ?: "0",
                        imageUrl = childSnapshot.child("Images").child("1").value?.toString() ?: ""
                    )
                    productList.add(product)
                }

                // Update the state after fetching
                allProducts.addAll(productList.shuffled()) // Shuffle for randomness
                loadMoreProducts() // Load the first 10 products
                isLoading.value = false // Stop loading
            } catch (e: Exception) {
                // Handle errors
                isLoading.value = false
            }
        }
    }


}