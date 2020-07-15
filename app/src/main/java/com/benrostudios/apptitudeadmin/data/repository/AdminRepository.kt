package com.benrostudios.apptitudeadmin.data.repository

import androidx.lifecycle.LiveData
import com.benrostudios.apptitudeadmin.data.models.AdminPanel

interface AdminRepository {
    suspend fun fetchAdminPanel()
    val adminPanelResult: LiveData<AdminPanel>
    suspend fun <T : Any> adminExecution(option: String, value: T)
    val adminExecutionResult: LiveData<Boolean>

}