package com.projects.quickbazaar


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

interface FirebaseManager {
    val auth: FirebaseAuth
    val database: DatabaseReference
}
