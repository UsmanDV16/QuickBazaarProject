package com.projects.quickbazaar

import com.google.firebase.auth.FirebaseAuth

fun signOutUser()
{
    val auth=FirebaseAuth.getInstance()
    auth.signOut()
}