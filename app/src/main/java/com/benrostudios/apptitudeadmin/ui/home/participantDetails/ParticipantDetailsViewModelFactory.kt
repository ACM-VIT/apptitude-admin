package com.benrostudios.apptitudeadmin.ui.home.participantDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.apptitudeadmin.data.repository.AdminRepository

@Suppress("unchecked_cast")
class ParticipantDetailsViewModelFactory(
    private val adminRepository: AdminRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ParticipantDetailsViewModel(adminRepository) as T
    }
}