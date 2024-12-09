package com.projects.quickbazaar

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordViewModel: ViewModel()
{
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun sendResetLink(email:String, onResult:(Boolean, String)->Unit)
    {
        auth.sendPasswordResetEmail(email).addOnSuccessListener(
        ){
            onResult(true, "Password reset link sent to your email address")
        }
            .addOnFailureListener(){
                onResult(false, it.message!!)
            }

    }
}