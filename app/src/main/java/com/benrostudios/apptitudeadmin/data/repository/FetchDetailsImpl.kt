package com.benrostudios.apptitudeadmin.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.apptitudeadmin.data.models.Participant
import com.benrostudios.apptitudeadmin.data.models.Team
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.temporal.ValueRange

class FetchDetailsImpl : FetchDetails {

    private val _participantsList = MutableLiveData<List<Participant>>()
    private val _teamsList = MutableLiveData<List<Team>>()
    private val _teamDetails = MutableLiveData<Team>()
    private lateinit var databaseReference: DatabaseReference


    @Suppress("Unchecked_cast")
    override suspend fun fetchParticipants() {
        val _participants: MutableList<Participant> = mutableListOf()
        databaseReference = Firebase.database.getReference("/participants")
        val participantFetcher = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (x in snapshot.children) {
                        val participant = x.getValue(Participant::class.java)
                        participant?.let { _participants.add(it) }
                    }
                    _participantsList.postValue(_participants)
                }
            }
        }
        databaseReference.addValueEventListener(participantFetcher)
    }

    @Suppress("Unchecked_cast")
    override suspend fun fetchTeams() {
        val _teams: MutableList<Team> = mutableListOf()
        databaseReference = Firebase.database.getReference("/teams")
        val teamsFetcher = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for(x in snapshot.children){
                        val team = x.getValue(Team::class.java).also {
                            it?.let {
                                it.membersDetails = participantFetcher(it.members)
                            }
                        }
                        team?.let { _teams.add(it) }

                    }
                    _teamsList.postValue(_teams)
                }
            }

        }
        databaseReference.addListenerForSingleValueEvent(teamsFetcher)
    }

    private fun participantFetcher(memberList: List<String>):MutableList<Participant>{
        val teamMember:MutableList<Participant> = mutableListOf()
        for(x in memberList){
            databaseReference = Firebase.database.getReference("/participants/$x")
            val participantFetcher = object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val member = snapshot.getValue(Participant::class.java)
                        member?.let { teamMember.add(it) }
                    }
                }

            }
            databaseReference.addValueEventListener(participantFetcher)
        }
        return teamMember
    }
    override suspend fun fetchTeamDetails(teamId: String) {
        databaseReference = Firebase.database.getReference("teams/$teamId")
        val teamDetailFetcher = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val teamDetails = snapshot.getValue(Team::class.java)
                    _teamDetails.postValue(teamDetails)
                }
            }

        }
        databaseReference.addListenerForSingleValueEvent(teamDetailFetcher)
    }

    override val participantList: LiveData<List<Participant>>
        get() = _participantsList
    override val teamsList: LiveData<List<Team>>
        get() = _teamsList
    override val teamDetails: LiveData<Team>
        get() = _teamDetails
}