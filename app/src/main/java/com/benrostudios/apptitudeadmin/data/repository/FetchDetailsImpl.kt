package com.benrostudios.apptitudeadmin.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.apptitudeadmin.data.models.Participant
import com.benrostudios.apptitudeadmin.data.models.Team
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FetchDetailsImpl : FetchDetails {

    private val _participantsList = MutableLiveData<List<Participant>>()
    private val _teamsList = MutableLiveData<List<Team>>()
    private lateinit var databaseReference: DatabaseReference


    @Suppress("Unchecked_cast")
    override suspend fun fetchParticipants() {
        databaseReference = Firebase.database.getReference("/participants")
        val participantFetcher = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val participantsList:List<Participant>? = snapshot.value as List<Participant> ?: emptyList()
                    _participantsList.postValue(participantsList)
                }
            }
        }
        databaseReference.addValueEventListener(participantFetcher)
    }

    @Suppress("Unchecked_cast")
    override suspend fun fetchTeams() {
        databaseReference = Firebase.database.getReference("/teams")
        val teamsFetcher = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val teamsList:List<Team>? = snapshot.value as List<Team> ?: emptyList()
                    _teamsList.postValue(teamsList)
                }
            }

        }
        databaseReference.addListenerForSingleValueEvent(teamsFetcher)
    }

    override val participantList: LiveData<List<Participant>>
        get() = _participantsList
    override val teamsList: LiveData<List<Team>>
        get() = _teamsList
}