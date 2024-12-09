package com.projects.quickbazaar

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

open class ProductViewModel: ViewModel() {
    protected val database: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Product")

    // Observable state for products and loading
    val products = mutableStateListOf<ProductHighlight>()
    val allProducts = mutableStateListOf<ProductHighlight>()
    var isLoading = mutableStateOf(false)
    var displayed=0



    fun loadMoreProducts() {
        val chunkSize = minOf(10, allProducts.size-displayed)
        if (chunkSize > 0) {
            products.addAll(allProducts.subList(displayed, displayed+chunkSize))
            displayed += chunkSize

        }
    }
}