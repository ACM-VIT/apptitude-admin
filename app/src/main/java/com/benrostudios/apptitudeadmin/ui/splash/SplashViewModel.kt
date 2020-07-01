package com.ieeevit.gakko.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benrostudios.apptitudeadmin.data.repository.AuthRepository

class SplashViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    val userResponse = MutableLiveData<Boolean>()

    init{
        authRepository.getRegisterStatus.observeForever {
            userResponse.postValue(it)
        }
    }

    suspend fun getUser(phone: String){
        authRepository.getUser(phone)
    }




}