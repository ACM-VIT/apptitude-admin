package com.benrostudios.apptitudeadmin.ui.home.teams

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benrostudios.apptitudeadmin.data.models.Team
import com.benrostudios.apptitudeadmin.data.repository.FetchDetails

class TeamsViewModel(
    private val fetchDetails: FetchDetails
) : ViewModel() {
    val teamsList = MutableLiveData<List<Team>>()

    init {
        fetchDetails.teamsList.observeForever {
            teamsList.postValue(it)
        }
    }

    suspend fun fetchTeams() {
        fetchDetails.fetchTeams()
    }
}