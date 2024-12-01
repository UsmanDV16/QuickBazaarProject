package com.projects.quickbazaar

import com.google.firebase.auth.FirebaseAuth

fun getCurrentUserId(): String? {
    val currentUser = FirebaseAuth.getInstance().currentUser
    return currentUser?.uid // Returns UID if user is logged in, otherwise null
}