package com.projects.quickbazaar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChangePasswordViewModel : ViewModel() {
    private val passwordManager = PasswordManager()

    private val _changePasswordStatus = MutableLiveData<Pair<Boolean, String?>>()
    val changePasswordStatus: LiveData<Pair<Boolean, String?>>
        get() = _changePasswordStatus

    fun changePassword(currentPassword: String, newPassword: String, confirmPassword: String) {
        passwordManager.changePassword(currentPassword, newPassword, confirmPassword) { isSuccess, message ->
            _changePasswordStatus.postValue(Pair(isSuccess, message))
        }
    }
}
