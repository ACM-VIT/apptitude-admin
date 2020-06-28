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

    override suspend fun fetchParticipants() {
        databaseReference = Firebase.database.getReference("/participants")
        val participantFetcher = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(snapshot: DataSnapshot) {

            }
        }
        databaseReference.addValueEventListener(participantFetcher)
    }

    override suspend fun fetchTeams() {
        databaseReference = Firebase.database.getReference("/teams")
        val teamsFetcher = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

        }
        databaseReference.addListenerForSingleValueEvent(teamsFetcher)
    }

    override val participantList: LiveData<List<Participant>>
        get() = _participantsList
    override val teamsList: LiveData<List<Team>>
        get() = _teamsList
}