package com.benrostudios.apptitudeadmin.ui.home.teamDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.apptitudeadmin.data.repository.FetchDetails


@Suppress("Unchecked_cast")
class TeamDetailsViewModelFactory(
    private val fetchDetails: FetchDetails
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TeamDetailsViewModel(fetchDetails) as T
    }
}