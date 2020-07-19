package com.benrostudios.apptitudeadmin.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.apptitudeadmin.data.repository.PushDetails.Companion.instance
import com.benrostudios.apptitudeadmin.utils.SharedPrefsUtils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class AdminPrivilegesImpl : AdminPrivileges {
    private val _incrementationStatus =  MutableLiveData<Boolean>()
    private lateinit var databaseReference: DatabaseReference
    override suspend fun incrementPrivileges(phone: String, curLevel: Int) {
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