package com.projects.quickbazaar

import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

fun fetchProductName(productId: String): String? {
    val database= Firebase.database.reference
    return try {
        val productRef = database.child("Product").child(productId).child("Name").get()
        productRef.result.value.toString()?:""
    } catch (e: Exception) {
        Log.e("FetchProductName", "Error fetching product name: ${e.message}")
        null
    }
}