package com.projects.quickbazaar

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CategoryViewModel : ViewModel() {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Category")

    // Observable state for categories and loading
    private val _categories = MutableStateFlow<List<MainCategory>>(emptyList())
    val categories: StateFlow<List<MainCategory>> = _categories

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading


    fun fetchCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val snapshot = database.get().await()
                val categoryList = mutableListOf<MainCategory>()

                for (mainCategorySnapshot in snapshot.children) {
                    val mainCategoryName = mainCategorySnapshot.key.orEmpty()
                    val subcategories = mutableListOf<SubCategory>()

                    for (subCategorySnapshot in mainCategorySnapshot.children) {
                        val subCategoryName = subCategorySnapshot.value?.toString() ?: "Unknown"
                        val subCategoryId = subCategorySnapshot.key ?: "Unknown"

                        subcategories.add(SubCategory(id = subCategoryId, name = subCategoryName))
                    }

                    categoryList.add(MainCategory(name = mainCategoryName, subcategories = subcategories))
                }

                // Update state
                _categories.value = categoryList
                _isLoading.value = false
            } catch (e: Exception) {
                // Handle errors
                _isLoading.value = false
            }
        }
    }
}