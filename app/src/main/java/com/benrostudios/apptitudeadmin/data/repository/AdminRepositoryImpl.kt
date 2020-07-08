package com.benrostudios.apptitudeadmin.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.apptitudeadmin.data.models.AdminPanel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AdminRepositoryImpl : AdminRepository {

    private val _adminPanelResult = MutableLiveData<AdminPanel>()
    private lateinit var databaseReference: DatabaseReference

    override suspend fun fetchAdminPanel() {
        databaseReference = Firebase.database.getReference("/adminControl")
        val fetchAdminPanel = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
               if(snapshot.exists()){
                   val adminPanel: AdminPanel? = snapshot.getValue(AdminPanel::class.java)
                   adminPanel?.let {
                       _adminPanelResult.postValue(it)
                   }
               }
            }

        }
        databaseReference.addListenerForSingleValueEvent(fetchAdminPanel)
    }

    override val adminPanelResult: LiveData<AdminPanel>
        get() = _adminPanelResult
}