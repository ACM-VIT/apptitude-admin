package com.benrostudios.apptitudeadmin.data.repository

import android.provider.LiveFolders
import androidx.lifecycle.LiveData

interface AdminPrivileges {
    suspend fun incrementPrivileges(phone: String, curLevel: Int)
    val incrementationStatus : LiveData<Boolean>
}