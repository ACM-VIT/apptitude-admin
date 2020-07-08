package com.benrostudios.apptitudeadmin.ui.home.advanced

import androidx.lifecycle.ViewModel
import com.benrostudios.apptitudeadmin.data.repository.AdminRepository

class AdvancedViewModel(
    private val adminRepository: AdminRepository
) : ViewModel() {

    val adminPanelResult
        get() = adminRepository.adminPanelResult

    suspend fun fetchAdminPanel(){
        adminRepository.fetchAdminPanel()
    }
}