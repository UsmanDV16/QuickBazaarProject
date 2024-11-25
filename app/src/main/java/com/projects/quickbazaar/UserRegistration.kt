package com.projects.quickbazaar

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserRegistration(override val auth: FirebaseAuth=FirebaseAuth.getInstance(), override val database: DatabaseReference= FirebaseDatabase.getInstance().getReference("Users")) : FirebaseManager
{
    fun signUpUser(
        name: String,
        email: String,
        phoneNo: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        // Check if email already exists in Firebase Auth
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: ""
                    val newUser = User(name, email, phoneNo)

                    // Save user to Realtime Database
                    database.child(userId).setValue(newUser)
                        .addOnCompleteListener { dbTask ->
                            if (dbTask.isSuccessful) {
                                onResult(true, null)
                            } else {
                                onResult(false, dbTask.exception?.message)
                            }
                        }
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }
}
