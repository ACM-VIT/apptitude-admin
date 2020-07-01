package com.benrostudios.apptitudeadmin.data.repository

import androidx.lifecycle.LiveData
import com.benrostudios.apptitudeadmin.data.models.Admin
import com.google.firebase.auth.PhoneAuthCredential

interface AuthRepository {
    suspend fun signIn(credential: PhoneAuthCredential)
    val getAuthStatus: LiveData<Boolean>
    suspend fun registerUser(user: Admin)
    val getRegisterStatus: LiveData<Boolean>
    suspend fun getUser(phone: String)
}