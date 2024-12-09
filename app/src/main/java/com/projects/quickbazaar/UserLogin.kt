package com.projects.quickbazaar

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserLogin(override val auth: FirebaseAuth = FirebaseAuth.getInstance(), override val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")) : FirebaseManager {

    fun loginUser(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Retrieve user data from Realtime Database if needed
                    //val userId = auth.currentUser?.uid ?: ""
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }
}