package com.benrostudios.apptitudeadmin.ui.auth.verification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benrostudios.apptitudeadmin.data.repository.AuthRepository
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.database.core.Repo

class VerificationViewModel(
  private val authRepository: AuthRepository
) : ViewModel() {

   val authResponse = MutableLiveData<Boolean>()

   init {
       authRepository.getAuthStatus.observeForever {
          authResponse.postValue(it)
       }
   }

   suspend fun getUser(phone: String){
      authRepository.getUser(phone)
   }

   suspend fun signInWithFirebase(credential: PhoneAuthCredential) {
      authRepository.signIn(credential)
   }
}