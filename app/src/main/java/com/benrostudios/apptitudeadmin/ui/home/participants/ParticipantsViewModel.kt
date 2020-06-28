package com.benrostudios.apptitudeadmin.ui.home.participants

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benrostudios.apptitudeadmin.data.models.Participant
import com.benrostudios.apptitudeadmin.data.repository.FetchDetails

class ParticipantsViewModel(
    private val fetchDetails: FetchDetails
) : ViewModel() {

    val participantsList =  MutableLiveData<List<Participant>>()
    init {
        fetchDetails.participantList.observeForever {
            participantsList.postValue(it)
        }
    }

    suspend fun fetchParticipants(){
        fetchDetails.fetchParticipants()
    }
}