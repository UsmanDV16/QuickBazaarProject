package com.projects.quickbazaar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AccountUpdateViewModel : ViewModel() {
    private val repository = AccountUpdate()

    private val _updateStatus = MutableLiveData<Pair<Boolean, String?>>()
    val updateStatus: LiveData<Pair<Boolean, String?>>
        get() = _updateStatus


    fun updateUser(
        userId: String,
        name: String,
        email: String,
        phoneNo: String,
        address: String
    ) {
        repository.updateUserInfo(userId, name, email, phoneNo, address) { isSuccess, error ->
            _updateStatus.postValue(Pair(isSuccess, error))
        }
    }
}
