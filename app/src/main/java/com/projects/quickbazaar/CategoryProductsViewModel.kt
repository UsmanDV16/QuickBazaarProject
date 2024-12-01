package com.projects.quickbazaar

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.reflect.Constructor

class CategoryProductsViewModel(private val categoryId: String):ProductViewModel() {

    init{
        fetchAllProducts(categoryId)
    }

    fun fetchAllProducts(categoryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try
            {
                // Fetch products where "categoryId" matches the selected category
                val snapshot = database.get().await()
                val productList = mutableListOf<ProductHighlight>()


                for (productSnapshot in snapshot.children) {
                    val productCategoryId = productSnapshot.child("CategoryID").value?.toString()?:""
                    if (productCategoryId == categoryId) {
                        val product = ProductHighlight(
                            ID=productSnapshot.key.toString(),
                            name = productSnapshot.child("Name").value?.toString() ?: "Unknown",
                            price = productSnapshot.child("Price").value?.toString() ?: "0",
                            imageUrl = productSnapshot.child("Images").child("1").value?.toString() ?: ""
                        )
                        productList.add(product)
                    }
                }

                allProducts.clear()
                allProducts.addAll(productList.shuffled()) // Shuffle for randomness
                loadMoreProducts() // Load the first 10 products
                isLoading.value = false
            } catch (e: Exception) {
                // Handle errors
                Log.e("Exception", e.toString())
                isLoading.value = false
            }
        }
    }


}