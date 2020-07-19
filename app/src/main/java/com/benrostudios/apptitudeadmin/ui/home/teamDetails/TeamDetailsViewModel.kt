package com.benrostudios.apptitudeadmin.ui.home.teamDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benrostudios.apptitudeadmin.data.repository.FetchDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamDetailsViewModel(
    private val fetchDetails: FetchDetails
) : ViewModel() {
    val teamDetails
        get() = fetchDetails.teamDetails

    fun fetchTeamDetails(teamId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchDetails.fetchTeamDetails(teamId)
        }

    }
}