package com.projects.quickbazaar

import com.google.firebase.database.FirebaseDatabase

fun getUserName(uId:String) :String{
    var toReturn=""
    val database = FirebaseDatabase.getInstance().getReference("Users/$uId")
    database.child("Name").get().addOnSuccessListener(){
        toReturn=it.value.toString()
    }
        return toReturn

}