package com.benrostudios.apptitudeadmin.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.apptitudeadmin.data.models.AdminPanel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.concurrent.timerTask

class AdminRepositoryImpl : AdminRepository {

    private val _adminPanelResult = MutableLiveData<AdminPanel>()
    private val _adminExecutionResult = MutableLiveData<Boolean>()
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

    override suspend fun adminExecution(option: String, value: String) {
        databaseReference = Firebase.database.getReference("/adminControl/$option")
        databaseReference.setValue(value).addOnCompleteListener {
            if(it.isSuccessful){
                _adminExecutionResult.postValue(true)
            }else{
                _adminExecutionResult.postValue(false)
            }
        }
    }

    override val adminExecutionResult: LiveData<Boolean>
        get() = _adminExecutionResult
}