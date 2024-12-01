package com.projects.quickbazaar

data class OrderDetails(
    val orderId: String,
    val status: String,
    val orderTime: String,
    val expectedArrival: String,
    val products: Map<String, Int>
)