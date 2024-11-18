package com.projects.quickbazaar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {

    private val repository = UserRegistration()

    private val _signUpStatus = MutableLiveData<Pair<Boolean, String?>>()
    val signUpStatus: LiveData<Pair<Boolean, String?>>
        get() = _signUpStatus

    fun signUp(name: String, email: String, phoneNo: String, password: String) {
        repository.signUpUser(name, email, phoneNo, password) { isSuccess, error ->
            _signUpStatus.postValue(Pair(isSuccess, error))
        }
    }
}