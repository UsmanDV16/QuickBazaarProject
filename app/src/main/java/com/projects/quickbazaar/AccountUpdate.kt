package com.projects.quickbazaar

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AccountUpdate(
    override val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    override val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
) : FirebaseManager {

    fun updateUserInfo(
        userId: String,
        name: String,
        email: String,
        phoneNo: String,
        address: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        val userUpdates = mapOf(
            "Name" to name,
            "Email" to email,
            "PhoneNo" to phoneNo,
            "Address" to address
        )

        database.child(userId).updateChildren(userUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }
}
