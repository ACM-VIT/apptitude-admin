package com.benrostudios.apptitudeadmin.ui.admin.adminUpgrade

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benrostudios.apptitudeadmin.data.repository.AdminPrivileges
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch

class AdminUpgradeViewModel(
    private val adminPrivileges: AdminPrivileges
) : ViewModel() {
   val getAdminIncrementationStatus
    get() = adminPrivileges.incrementationStatus

    fun upgradeAdmin(phone: String , level: Int){
        viewModelScope.launch(Dispatchers.IO ){
            adminPrivileges.incrementPrivileges(phone,level)
        }
    }
}