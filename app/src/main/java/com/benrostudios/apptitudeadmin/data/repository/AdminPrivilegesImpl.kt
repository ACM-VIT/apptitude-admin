package com.benrostudios.apptitudeadmin.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class AdminPrivilegesImpl : AdminPrivileges {
    private val _incrementationStatus =  MutableLiveData<Boolean>()
    private lateinit var databaseReference: DatabaseReference

    override suspend fun incrementPrivileges(phone: String, curLevel: Int) {
        Log.d("pri", phone)
        databaseReference = Firebase.database.getReference("/admins/$phone")
        databaseReference.child("level").setValue(curLevel).addOnSuccessListener {
            _incrementationStatus.postValue(true)
        }.addOnFailureListener{
            _incrementationStatus.postValue(false)
        }
    }

    override val incrementationStatus: LiveData<Boolean>
        get() = _incrementationStatus
}