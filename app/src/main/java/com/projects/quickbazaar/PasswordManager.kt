package com.projects.quickbazaar
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class PasswordManager {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun changePassword(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String,
        callback: (Boolean, String?) -> Unit
    ) {
        val user = auth.currentUser

        if (user == null) {
            callback(false, "User not logged in.")
            return
        }

        if (newPassword != confirmPassword) {
            callback(false, "New password and confirm password do not match.")
            return
        }

        val email = user.email
        if (email.isNullOrEmpty()) {
            callback(false, "User email is not available.")
            return
        }

        // Re-authenticate the user with the current password
        val credential = EmailAuthProvider.getCredential(email, currentPassword)
        user.reauthenticate(credential)
            .addOnCompleteListener { reauthTask ->
                if (reauthTask.isSuccessful) {
                    // Re-authentication successful; update password
                    user.updatePassword(newPassword)
                        .addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                callback(true, "Password updated successfully.")
                            } else {
                                callback(false, "Failed to update password: ${updateTask.exception?.message}")
                            }
                        }
                } else {
                    // Re-authentication failed
                    callback(false, "Current password is incorrect.")
                }
            }
    }
}
