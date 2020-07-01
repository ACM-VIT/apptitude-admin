package com.benrostudios.apptitudeadmin.ui.auth.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.apptitudeadmin.data.repository.AuthRepository
import com.google.firebase.database.core.Repo


@Suppress("Unchecked_cast")
class ProfileViewModelFactory(
    private val authRepository: AuthRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(authRepository) as T
    }
}