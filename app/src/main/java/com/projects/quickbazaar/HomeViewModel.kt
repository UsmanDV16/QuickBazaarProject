package com.projects.quickbazaar

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel : ProductViewModel() {

    fun searchProducts(query: String): List<ProductHighlight> {
        val tokens = query.trim().split("\\s+".toRegex())

        return allProducts.filter { product ->
            tokens.any { token ->
                product.name.contains(token, ignoreCase = true)

            }
        }
    }

    fun fetchAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
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

                allProducts.addAll(productList.shuffled())
                loadMoreProducts()
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }


}