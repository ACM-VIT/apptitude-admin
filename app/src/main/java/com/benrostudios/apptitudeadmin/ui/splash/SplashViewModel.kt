package com.benrostudios.apptitudeadmin.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benrostudios.apptitudeadmin.data.repository.AuthRepository

class SplashViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    val userResponse
    get() = authRepository.getRegisterStatus



    suspend fun getUser(phone: String){
        authRepository.getUser(phone)
    }




}