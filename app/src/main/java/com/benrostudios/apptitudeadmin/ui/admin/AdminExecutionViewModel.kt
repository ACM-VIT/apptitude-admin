package com.benrostudios.apptitudeadmin.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benrostudios.apptitudeadmin.data.repository.AdminRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminExecutionViewModel(
    private val adminRepository: AdminRepository
) : ViewModel() {
    val result
        get() = adminRepository.adminExecutionResult


    fun <T: Any> executeOperation(option:String, value: T){
        viewModelScope.launch(Dispatchers.IO){
            adminRepository.adminExecution(option , value)
        }
    }
}