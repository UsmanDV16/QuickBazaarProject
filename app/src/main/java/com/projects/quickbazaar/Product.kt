package com.projects.quickbazaar

data class Product(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Int = 0,
    val images: List<String> = emptyList(),
    val rating: Float = 0f,
    val totalReviews: Int = 0,
    val category: String = ""
)