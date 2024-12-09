package com.projects.quickbazaar

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf

data class CartItem(
    val productId: String,
    val name: String,
    val imageUrl: String,
    val price: Int,
    var quantity: MutableIntState =mutableIntStateOf(0)
)