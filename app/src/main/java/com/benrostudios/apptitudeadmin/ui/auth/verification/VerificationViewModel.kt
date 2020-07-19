package com.benrostudios.apptitudeadmin.ui.auth.verification


import androidx.lifecycle.ViewModel
import com.benrostudios.apptitudeadmin.data.repository.AuthRepository
import com.google.firebase.auth.PhoneAuthCredential
class VerificationViewModel(
  private val authRepository: AuthRepository
) : ViewModel() {

   val authResponse
    get() = authRepository.getAuthStatus

    val userChecker
        get() = authRepository.getRegisterStatus

   suspend fun getUser(phone: String){
      authRepository.getUser(phone)
   }

   suspend fun signInWithFirebase(credential: PhoneAuthCredential) {
      authRepository.signIn(credential)
   }
}