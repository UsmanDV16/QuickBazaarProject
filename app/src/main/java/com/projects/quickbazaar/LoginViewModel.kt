package com.projects.quickbazaar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val repository = UserLogin()

    private val _loginStatus = MutableLiveData<Pair<Boolean, String?>>()
    val loginStatus: LiveData<Pair<Boolean, String?>>
        get() = _loginStatus

    fun login(email: String, password: String) {
        repository.loginUser(email, password) { isSuccess, error ->
            _loginStatus.postValue(Pair(isSuccess, error))
        }
    }
}