package com.projects.quickbazaar

import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CartViewModel : ViewModel() {
    val database = Firebase.database.reference

    val _cartItems = mutableStateListOf<CartItem>()

    val _subtotal = mutableIntStateOf(0)
    var isLoading = mutableStateOf(false)

    val _deliveryCharges = mutableIntStateOf(0)
    fun fetchCart(userId: String) {
        isLoading.value=true
        database.child("Users").child(userId).child("Cart")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var total = 0

                    snapshot.children.forEach { cartSnapshot ->
                        val productId = cartSnapshot.key ?: return@forEach
                        val quantity = cartSnapshot.getValue(Int::class.java) ?: 0

                        database.child("Product").child(productId).addListenerForSingleValueEvent(object : ValueEventListener {
                            @RequiresApi(35)
                            override fun onDataChange(productSnapshot: DataSnapshot) {
                                val name = productSnapshot.child("Name").getValue(String::class.java) ?: "Unknown"
                                val imageUrl = productSnapshot.child("Images").child("1").getValue(String::class.java) ?: ""
                                val price = productSnapshot.child("Price").getValue(Int::class.java) ?: 0

                                _subtotal.intValue += price * quantity

                                _cartItems.add(CartItem(productId, name, imageUrl, price, mutableIntStateOf(quantity)))

                            }
                            override fun onCancelled(error: DatabaseError) {
                                Log.e("CartViewModel", "Error fetching product: ${error.message}")
                            }
                        })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("CartViewModel", "Error fetching cart: ${error.message}")
                }
            })

        database.child("DeliveryCharges").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val charges = snapshot.getValue(Int::class.java) ?: 200
                _deliveryCharges.value=charges
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("CartViewModel", "Error fetching delivery charges: ${error.message}")
            }
        })
        isLoading.value=false
    }

    fun updateQuantity(userId: String, productId: String, newQuantity: Int, increase:Boolean) {
        val cartRef = database.child("Users").child(userId).child("Cart").child(productId)

        if (newQuantity > 0) {
            val index=_cartItems.indexOfFirst { it.productId==productId }

            if(increase)
                _subtotal.intValue+=_cartItems[index].price
            else
                _subtotal.intValue -= _cartItems[index].price
            cartRef.setValue(newQuantity)
        } else {
            cartRef.removeValue()
            _cartItems.removeIf { it.productId==productId }
            val index=_cartItems.indexOfFirst { it.productId==productId }
            _subtotal.intValue-=_cartItems[index].price
        }
    }
}


