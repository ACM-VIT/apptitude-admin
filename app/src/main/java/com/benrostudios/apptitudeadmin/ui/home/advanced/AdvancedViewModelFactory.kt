package com.benrostudios.apptitudeadmin.ui.home.advanced

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.apptitudeadmin.data.repository.AdminRepository


@Suppress("Unchecked_cast")
class AdvancedViewModelFactory(
    private val adminRepository: AdminRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AdvancedViewModel(adminRepository) as T
    }

}