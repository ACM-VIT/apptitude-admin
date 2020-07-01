package com.benrostudios.apptitudeadmin.ui.auth.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benrostudios.apptitudeadmin.data.models.Admin
import com.benrostudios.apptitudeadmin.data.repository.AuthRepository

class ProfileViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    val registerResponse = MutableLiveData<Boolean>()

    init {
        authRepository.getRegisterStatus.observeForever {
            registerResponse.postValue(it)
        }
    }
    suspend fun registerUser(user: Admin){
        authRepository.registerUser(user)
    }
}