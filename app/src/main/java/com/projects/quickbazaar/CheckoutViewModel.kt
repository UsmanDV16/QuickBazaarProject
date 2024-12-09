package com.projects.quickbazaar

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCanceledListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

class CheckoutViewModel : ViewModel() {
    private val database = Firebase.database.getReference()

    val _totalAmount = MutableLiveData<Int>()
    val totalAmount: LiveData<Int>
        get() = _totalAmount

     var subtotal: Int = 0
    var deliveryCharges: Int = 0
    var discount=MutableLiveData(0)
    var _couponCode= MutableLiveData("")
    var isLoaded= mutableStateOf(false)
    var orderTimeAndDate:String=""
    var orderId:String=""

    fun setAmounts(subtotal: Int, deliveryCharges: Int) {
        this.subtotal = subtotal
        this.deliveryCharges = deliveryCharges
        calculateTotal()
        isLoaded.value=true
    }

    fun applyCoupon(couponCode: String, onResult:(String?)->Unit) {
        if(couponCode!=""&&couponCode!=_couponCode.value) {
            database.child("Coupons/$couponCode").get().addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.exists()) {
                    val discountPercentage = task.result.getValue(Int::class.java) ?: -1
                    if (discountPercentage in 1..100) {
                        discount.value = discountPercentage
                        calculateTotal()
                        onResult("Coupon successfully applied")
                        _couponCode.value = couponCode
                    }
                } else if (!task.result.exists()) {
                    onResult("Invalid coupon code")
                } else if (!task.isSuccessful) {
                    onResult(task.exception?.message)
                }

            }
        }



    }

    fun removeCoupon() {
        discount.value = 0
        _couponCode.value=""
        calculateTotal()
    }

    private fun calculateTotal() {
        val discountAmount = ((subtotal + deliveryCharges)* discount.value!!) / 100
        _totalAmount.postValue(subtotal + deliveryCharges - discountAmount)
    }


    fun placeOrder(
        userId: String,
        paymentMethod: String, // Cash or Card
        address: String,
        onOrderPlaced: () -> Unit
    ) {
        val orderRef = database.child("Order").push() // Generate unique OrderID
        orderId = orderRef.key!!

        val currentTime = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("HH:mm:ss, dd-MM-yyyy", Locale.getDefault())
        val orderTime = dateFormat.format(Date(currentTime))
        orderTimeAndDate=orderTime
        database.child("Users").child(userId).child("Cart")
            .addListenerForSingleValueEvent(object : com.google.firebase.database.ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val products = mutableMapOf<String, Int>()

                    snapshot.children.forEach { productSnapshot ->
                        val productId = productSnapshot.key ?: return@forEach
                        val quantity = productSnapshot.getValue(Int::class.java) ?: 0
                        products[productId] = quantity
                    }

                    val orderData = mapOf(
                        "Amount" to totalAmount.value,
                        "Payment_By" to paymentMethod,
                        "Order_Time_and_Date" to orderTime,
                        "Address" to address,
                        "Status" to "Received/In-Progress",
                        "UID" to userId,
                        "Products" to products
                    )

                    // Write Order Data to Database
                    orderRef.setValue(orderData).addOnSuccessListener {
                        // Clear Cart
                        database.child("Previous_Orders").updateChildren(mapOf(Pair<String, String>(orderId, ""))).addOnSuccessListener {
                            // Clear Cart
                            database.child("Users").child(userId).child("Cart").removeValue()
                            onOrderPlaced()
                        }.addOnFailureListener { error ->
                            Log.e("CheckoutViewModel", "Error adding to Previous Orders: ${error.message}")
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("CheckoutViewModel", "Error fetching cart: ${error.message}")
                }
            })
    }
}
