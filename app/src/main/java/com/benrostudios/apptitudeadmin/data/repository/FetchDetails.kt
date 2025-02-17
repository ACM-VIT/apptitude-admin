package com.benrostudios.apptitudeadmin.data.repository

import androidx.lifecycle.LiveData
import com.benrostudios.apptitudeadmin.data.models.Participant
import com.benrostudios.apptitudeadmin.data.models.Team
import java.lang.invoke.CallSite

interface FetchDetails {
    suspend fun fetchParticipants()
    suspend fun fetchTeams()
    suspend fun fetchTeamDetails(teamId: String)
    val participantList: LiveData<List<Participant>>
    val teamsList: LiveData<List<Team>>
    val teamDetails: LiveData<Team>
}