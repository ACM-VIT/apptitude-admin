package com.benrostudios.apptitudeadmin.ui.home.teams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.apptitudeadmin.data.repository.FetchDetails
import com.benrostudios.apptitudeadmin.ui.home.participants.ParticipantsViewModel


@Suppress("Unchecked_Cast")
class TeamsViewModelFactory(
    private val fetchDetails: FetchDetails
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TeamsViewModel(fetchDetails) as T
    }
}
