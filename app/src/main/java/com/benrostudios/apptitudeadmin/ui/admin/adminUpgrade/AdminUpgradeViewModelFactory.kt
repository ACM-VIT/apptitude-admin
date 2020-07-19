package com.benrostudios.apptitudeadmin.ui.admin.adminUpgrade

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.apptitudeadmin.data.repository.AdminPrivileges


@Suppress("unchecked_cast")
class AdminUpgradeViewModelFactory(
    private val adminPrivileges: AdminPrivileges
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AdminUpgradeViewModelFactory(adminPrivileges) as T
    }
}