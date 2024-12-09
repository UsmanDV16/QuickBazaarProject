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

        val currentOrdersRef = database.child("Order")
        val previousOrdersRef = database.child("Users/$userId/Previous_Orders")

        previousOrdersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(previousSnapshot: DataSnapshot) {
                val previousOrderIds = previousSnapshot.children.mapNotNull { it.key }.toSet()

                currentOrdersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(currentSnapshot: DataSnapshot) {
                        currentSnapshot.children.forEach { orderSnapshot ->
                            val orderId = orderSnapshot.key ?: return@forEach
                            if (previousOrderIds.contains(orderId)) {
                                val status = orderSnapshot.child("Status").getValue(String::class.java) ?: "Unknown"
                                val timeDate = orderSnapshot.child("Order_Time_and_Date").getValue(String::class.java) ?: "Unknown"
                                val userIdFromDb = orderSnapshot.child("UID").getValue(String::class.java) ?: ""
                                val amount=orderSnapshot.child("Amount").value.toString()?:""
                                val productMap = mutableMapOf<String, Pair<Int, String>>()

                                orderSnapshot.child("Product").children.forEach { productSnapshot ->
                                    val productId = productSnapshot.key ?: return@forEach
                                    val quantity = productSnapshot.getValue(Int::class.java) ?: 0
                                    productMap[productId] = Pair<Int, String>(quantity, fetchProductName(productId)!!)
                                }

                                if (userIdFromDb == userId) {
                                    orders.add(Order(orderId, status, timeDate, productMap, amount))
                                }
                            }
                        }

                        // Post the combined list of orders
                        _orderList.postValue(orders)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("OrderHistoryViewModel", "Error fetching current orders: ${error.message}")
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("OrderHistoryViewModel", "Error fetching previous orders: ${error.message}")
            }
        })
    }

}


