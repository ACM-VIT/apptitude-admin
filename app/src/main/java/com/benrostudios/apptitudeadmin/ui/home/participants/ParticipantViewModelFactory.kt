package com.benrostudios.apptitudeadmin.ui.home.participants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.apptitudeadmin.data.repository.FetchDetails

@Suppress("Unchecked_Cast")
class ParticipantViewModelFactory(
    private val fetchDetails: FetchDetails
):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ParticipantsViewModel(fetchDetails) as T
    }
}