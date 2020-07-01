package com.benrostudios.apptitudeadmin.ui.auth.verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.apptitudeadmin.data.repository.AuthRepository
import com.google.firebase.database.core.Repo


@Suppress("Unchecked_cast")
class VerificationViewModelFactory(
    private val authRepository: AuthRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VerificationViewModel(authRepository) as T
    }
}