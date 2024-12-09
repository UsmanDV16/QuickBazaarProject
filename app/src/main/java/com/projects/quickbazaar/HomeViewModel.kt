package com.projects.quickbazaar

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class HomeViewModel : ProductViewModel() {

    var _latestOrderId = mutableStateOf<String>("")
    var fetched=false
    //var isFetched=false

    fun fetchLatestOrder(userId: String) {
        val previousOrdersRef = database.child("Previous_Orders")

        // Step 1: Fetch all order IDs from Previous_Orders
        previousOrdersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val orderIds = mutableListOf<String>()

                snapshot.children.forEach { orderSnapshot ->
                    val orderId = orderSnapshot.key ?: return@forEach
                    orderIds.add(orderId)
                }

                if (orderIds.isEmpty()) {
                    _latestOrderId.value=""
                    fetched=true
                    return
                }

                // Step 2: Fetch details for each order ID and find the latest one
                var latestOrder: Pair<String, Long>? = null // Pair<OrderID, Timestamp>
                val ordersRef = database.child("Order")

                ordersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(orderSnapshot: DataSnapshot) {
                        orderIds.forEach { orderId ->
                            val orderData = orderSnapshot.child(orderId)
                            val orderTime = orderData.child("Order_Time_and_Date").getValue(String::class.java)
                            val timestamp = parseOrderTimestamp(orderTime)

                            if (timestamp != null) {
                                if (latestOrder == null || timestamp > latestOrder!!.second) {
                                    latestOrder = Pair(orderId, timestamp)
                                }
                            }
                        }

                        // Save the latest order ID
                        _latestOrderId.value=latestOrder!!.first
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("HomeViewModel", "Error fetching order details: ${error.message}")
                        _latestOrderId.value=""
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HomeViewModel", "Error fetching previous orders: ${error.message}")
                _latestOrderId.value=""
            }
        })
    }

    private fun parseOrderTimestamp(orderTime: String?): Long? {
        return try {
            val dateFormat = SimpleDateFormat("HH:mm:ss, dd-MM-yyyy", Locale.getDefault())
            val date = dateFormat.parse(orderTime ?: return null)
            date?.time
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Error parsing order timestamp: ${e.message}")
            null
        }
    }



    fun searchProducts(query: String): List<ProductHighlight> {
        val tokens = query.trim().split("\\s+".toRegex())

        return allProducts.filter { product ->
            tokens.any { token ->
                product.name.contains(token, ignoreCase = true)

            }
        }
    }

    fun fetchAllProducts() {
        isLoading.value=true
        viewModelScope.launch(Dispatchers.IO) {
            /*if(!allProducts.isEmpty()) {
                allProducts.clear()

                products.clear()
                displayed = 0
            }*/
            try {
                val snapshot = database.get().await()
                val productList = mutableListOf<ProductHighlight>()

                for (childSnapshot in snapshot.children) {
                    val product = ProductHighlight(
                        ID=childSnapshot.key.toString(),

                        name = childSnapshot.child("Name").value?.toString() ?: "Unknown",
                        price = childSnapshot.child("Price").value?.toString() ?: "0",
                        imageUrl = childSnapshot.child("Images").child("1").value?.toString() ?: ""
                    )
                    productList.add(product)
                }

                allProducts.addAll(productList.shuffled())
                loadMoreProducts()
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }


}