package com.benrostudios.apptitudeadmin.ui.home.participantDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benrostudios.apptitudeadmin.data.repository.AdminRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ParticipantDetailsViewModel(
    private val adminRepository: AdminRepository
) : ViewModel() {
    val removeStatus
        get() = adminRepository.adminRemovePanelResult

    fun removeParticipant(uid: String){
        viewModelScope.launch(Dispatchers.IO){
            removeParticipant(uid)
        }
    }
}