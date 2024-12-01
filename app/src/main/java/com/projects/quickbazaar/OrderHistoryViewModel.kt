package com.projects.quickbazaar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class OrderHistoryViewModel : ViewModel() {
    private val database = Firebase.database.reference

    private val _orderList = MutableLiveData<List<Order>>()
    val orderList: LiveData<List<Order>>
        get() = _orderList



    fun fetchOrders(userId: String) {
        val orders = mutableListOf<Order>()

        // Fetch current orders and previous orders in parallel
        val currentOrdersRef = database.child("Orders")
        val previousOrdersRef = database.child("Users").child(userId).child("Previous_Orders")

        currentOrdersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(currentSnapshot: DataSnapshot) {
                // Add current orders to the list
                currentSnapshot.children.forEach { orderSnapshot ->
                    val orderId = orderSnapshot.key ?: return@forEach
                    val userIdFromDb = orderSnapshot.child("UserID").getValue(String::class.java) ?: ""

                    // Include only orders for the given user
                    if (userIdFromDb == userId) {
                        val status = orderSnapshot.child("Status").getValue(String::class.java) ?: "Unknown"
                        val timeDate = orderSnapshot.child("Order_Time_and_Date").getValue(String::class.java) ?: "Unknown"
                        val productMap = mutableMapOf<String, Int>()

                        orderSnapshot.child("Products").children.forEach { productSnapshot ->
                            val productId = productSnapshot.key ?: return@forEach
                            val quantity = productSnapshot.getValue(Int::class.java) ?: 0
                            productMap[productId] = quantity
                        }

                        orders.add(Order(orderId, status, timeDate, productMap))
                    }
                }

                // Fetch previous orders once current orders are processed
                previousOrdersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(previousSnapshot: DataSnapshot) {
                        // Add previous orders to the list
                        previousSnapshot.children.forEach { orderSnapshot ->
                            val orderId = orderSnapshot.key ?: return@forEach
                            val status = orderSnapshot.child("Status").getValue(String::class.java) ?: "Unknown"
                            val timeDate = orderSnapshot.child("Order_Time_and_Date").getValue(String::class.java) ?: "Unknown"
                            val productMap = mutableMapOf<String, Int>()

                            orderSnapshot.child("Products").children.forEach { productSnapshot ->
                                val productId = productSnapshot.key ?: return@forEach
                                val quantity = productSnapshot.getValue(Int::class.java) ?: 0
                                productMap[productId] = quantity
                            }

                            // Avoid duplicates by checking if the order ID already exists
                            if (orders.none { it.orderId == orderId }) {
                                orders.add(Order(orderId, status, timeDate, productMap))
                            }
                        }

                        // Update LiveData with the combined list of orders
                        _orderList.postValue(orders)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("OrderHistoryViewModel", "Error fetching previous orders: ${error.message}")
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("OrderHistoryViewModel", "Error fetching current orders: ${error.message}")
            }
        })
    }
}


