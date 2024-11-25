package com.projects.quickbazaar

data class MainCategory(
    val name: String,
    val subcategories: List<SubCategory>
)

data class SubCategory(
    val id: String,
    val name: String
)