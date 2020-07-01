package com.benrostudios.apptitudeadmin.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.apptitudeadmin.data.models.Admin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AuthRepositoryImpl : AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val AUTH_REPO_TAG = "AuthRepo"
    private lateinit var firebaseDatabase: DatabaseReference
    private val _authResponse = MutableLiveData<Boolean>()
    private val _registerResponse = MutableLiveData<Boolean>()



    override suspend fun signIn(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Log.d(AUTH_REPO_TAG, "signInWithCredential:success")
                    val user = task.result?.user
                    _authResponse.postValue(true)
                } else {
                    Log.w(AUTH_REPO_TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        _authResponse.postValue(false)
                    }
                }
            }
    }

    override val getAuthStatus: LiveData<Boolean>
        get() = _authResponse

    override suspend fun registerUser(user: Admin) {
        firebaseDatabase = Firebase.database.getReference("/admins")
        firebaseDatabase.child(user.phone).setValue(user).addOnCompleteListener {
            if(it.isSuccessful){
                _registerResponse.postValue(true)
            }else{
                _registerResponse.postValue(false)
            }
        }
    }

    override val getRegisterStatus: LiveData<Boolean>
        get() = _registerResponse

    override suspend fun getUser(phone: String) {
        firebaseDatabase = Firebase.database.getReference("/admins/$phone")
        var userFetcher = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    _registerResponse.postValue(true)
                }else{
                    _registerResponse.postValue(false)
                }
            }

        }
    }
}