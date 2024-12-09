package com.projects.quickbazaar

data class Product(
    var id:String="",
    val name: String = "",
    val categoryID: String="",
    val description: String = "",
    val price: Int = 0,
    val images: List<String> = emptyList(),
    val rating: Float = 0f,
    val totalReviews: Int = 0,
)