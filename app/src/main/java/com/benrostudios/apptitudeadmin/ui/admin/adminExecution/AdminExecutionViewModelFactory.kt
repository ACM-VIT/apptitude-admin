package com.benrostudios.apptitudeadmin.ui.admin.adminExecution

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.apptitudeadmin.data.repository.AdminRepository

class AdminExecutionViewModelFactory(
    private val adminRepository: AdminRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AdminExecutionViewModel(
            adminRepository
        ) as T
    }
}