package com.projects.quickbazaar

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


class ProductDetailViewModel(private val pId: String) : ProductViewModel() {

    private val _product = MutableStateFlow(Product())
    val product: StateFlow<Product> = _product

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews

    private val _exploreProducts = MutableStateFlow<List<ProductHighlight>>(emptyList())
    val exploreProducts: StateFlow<List<ProductHighlight>> = _exploreProducts

    private val _isAddedToCart = MutableStateFlow(false)
    val isAddedToCart: StateFlow<Boolean> = _isAddedToCart

    init {
        loadProduct(pId)
    }
    fun loadProduct(productId: String) {
        // Fetch product details from Firebase
        Firebase.database.getReference("Product/$productId").get().addOnSuccessListener { snapshot ->
            val product = snapshot.getValue(Product::class.java)!!
            _product.value = product
            loadReviews(productId)
            fetchProducts()
        }
    }

    private fun loadReviews(productId: String) {
        Firebase.database.getReference("Product/$productId/Reviews").get().addOnSuccessListener { snapshot ->
            val reviews = snapshot.children.map { it.getValue(Review::class.java)!! }
            _reviews.value = reviews
        }
    }

    fun fetchProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val prod_ref=Firebase.database.getReference("Product")

            try {
                // Fetch products from Firebase
                val snapshot = prod_ref.get().await()
                val productList = mutableListOf<ProductHighlight>()

                for (childSnapshot in snapshot.children) {
                    if(childSnapshot.child("CategoryID").value?.toString()==_product.value.category) {
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

    fun addToCart(productId: String) {
        val userId = Firebase.auth.currentUser?.uid ?: return
        val userCartRef = Firebase.database.getReference("User/$userId/Cart")
        userCartRef.child(productId).setValue(1).addOnSuccessListener {
            _isAddedToCart.value = true
        }
    }
}
