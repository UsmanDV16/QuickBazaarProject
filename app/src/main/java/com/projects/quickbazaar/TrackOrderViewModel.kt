package com.projects.quickbazaar

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.Calendar
import java.util.Locale

class  TrackOrderViewModel : ViewModel() {
    private val database = Firebase.database.reference

    val _orderDetails = MutableLiveData<OrderDetails>()

    fun fetchOrderDetails(orderId: String) {
        database.child("Order").child(orderId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val orderTime = snapshot.child("Order_Time_and_Date").getValue(String::class.java) ?: "Unknown"
                    val status = snapshot.child("Status").getValue(String::class.java) ?: "Unknown"
                    val userId = snapshot.child("UserID").getValue(String::class.java) ?: "Unknown"
                    val productMap = mutableMapOf<String, Int>()

                    snapshot.child("Products").children.forEach { productSnapshot ->
                        val productId = productSnapshot.key ?: return@forEach
                        val quantity = productSnapshot.getValue(Int::class.java) ?: 0
                        productMap[productId] = quantity
                    }

                    val expectedArrival = calculateExpectedArrival(orderTime)
                    _orderDetails.postValue(OrderDetails(orderId, status, orderTime, expectedArrival, productMap))
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("TrackOrderViewModel", "Error fetching order details: ${error.message}")
                }
            })
    }


    private fun calculateExpectedArrival(orderTime: String): String {
        return try {
            val dateFormat = SimpleDateFormat("dd/mm/yy", Locale.getDefault())
            val orderDate = dateFormat.parse(orderTime)
            val calendar = Calendar.getInstance()
            calendar.time = orderDate!!
            calendar.add(Calendar.DATE, 5)
            dateFormat.format(calendar.time)
        } catch (e: Exception) {
            "Unknown"
        }
    }
}


