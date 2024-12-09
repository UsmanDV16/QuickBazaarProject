package com.projects.quickbazaar

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class ProductDetailViewModel() : ProductViewModel() {

    val _product = mutableStateOf(Product())

    val _reviews = mutableStateListOf<Review>()

    val _exploreProducts = mutableStateListOf<ProductHighlight>()

    val _isAddedToCart = mutableStateOf(false)

    var totalReviews = mutableIntStateOf(0)
    var avgRating = mutableIntStateOf(0)

    fun loadProduct(productId: String) {

        Firebase.database.getReference("Product/$productId").get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val name = snapshot.child("Name").value?.toString() ?: "Unknown"
                    val price = snapshot.child("Price").value?.toString()?.toIntOrNull() ?: 0
                    val description =
                        snapshot.child("Description").value?.toString() ?: "No description"
                    val images =
                        snapshot.child("Images").children.mapNotNull { it.value?.toString() }
                    val categoryID = snapshot.child("CategoryID").value?.toString() ?: ""

                    val product = Product(
                        id = productId,
                        name = name,
                        price = price,
                        description = description,
                        categoryID = categoryID,
                        images = images
                    )

                    _product.value = product
                    loadReviews(productId)
                    fetchProducts()
                } else {
                    Log.e("loadProduct", "No product found with ID $productId")
                }
            }.addOnFailureListener { exception ->
            Log.e("loadProduct", "Error loading product: ${exception.message}")
        }
    }


    private fun loadReviews(productId: String) {
        Firebase.database.getReference("Product/$productId/Reviews").get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    try {
                        val reviews = mutableListOf<Review>()
                        var ratingSum = 0

                        for (reviewSnapshot in snapshot.children) {
                            val userId = reviewSnapshot.key ?: "Unknown User"
                            val ratingAndText = reviewSnapshot.children.firstOrNull()
                            val rating = ratingAndText?.key?.toIntOrNull() ?: 0
                            val text = ratingAndText?.value?.toString().orEmpty()

                            if (rating > 0) { // Only add valid reviews
                                ratingSum += rating
                                totalReviews.intValue++

                                _reviews.add(
                                    Review(
                                        username = getUserName(userId),
                                        rating = rating,
                                        text = text
                                    )
                                )
                            }
                        }
                        avgRating.intValue = if (totalReviews.intValue > 0) ratingSum / totalReviews.intValue else 0
                    } catch (e: Exception) {
                        Log.e("loadReviews", "Error parsing reviews: ${e.message}")
                    }
                } else {
                    Log.e("loadReviews", "No reviews found for product ID $productId")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("loadReviews", "Error loading reviews: ${exception.message}")
            }
    }


    fun fetchProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val prod_ref = Firebase.database.getReference("Product")

            try {
                // Fetch products from Firebase
                val snapshot = prod_ref.get().await()
                val productList = mutableListOf<ProductHighlight>()

                for (childSnapshot in snapshot.children) {
                    if (childSnapshot.child("CategoryID").value?.toString() == _product.value.categoryID) {
                        val product = ProductHighlight(
                            name = childSnapshot.child("Name").value?.toString() ?: "Unknown",
                            price = childSnapshot.child("Price").value?.toString() ?: "0",
                            imageUrl = childSnapshot.child("Images").child("1").value?.toString()
                                ?: ""
                        )
                        productList.add(product)
                    }
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

    fun is_in_cart(productId: String) {
        var userCartRef = Firebase.database.getReference("Users/${getCurrentUserId()}")

        userCartRef.child("Cart/$productId").get()
            .addOnSuccessListener { snapshot ->
                _isAddedToCart.value = snapshot.exists()
            }
    }
        fun addToCart(productId: String) {
            var userCartRef = Firebase.database.getReference("Users/${getCurrentUserId()}")

            userCartRef.child("Cart").get()
                .addOnSuccessListener {
                        userCartRef.child("Cart").updateChildren(mapOf(Pair<String, Int>(productId, 1)))
                            .addOnSuccessListener {
                                _isAddedToCart.value = true
                            }

                }
        }
    }

