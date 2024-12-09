package com.projects.quickbazaar

data class Order(
    val orderId: String,
    val status: String,
    val timeDate: String,
    val products: Map<String, Pair<Int, String>>,
    val Amount: String
)