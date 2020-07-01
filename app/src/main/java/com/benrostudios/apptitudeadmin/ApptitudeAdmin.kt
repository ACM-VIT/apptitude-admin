package com.benrostudios.apptitudeadmin

import android.app.Application
import com.benrostudios.apptitudeadmin.data.repository.AuthRepository
import com.benrostudios.apptitudeadmin.data.repository.AuthRepositoryImpl
import com.benrostudios.apptitudeadmin.data.repository.FetchDetails
import com.benrostudios.apptitudeadmin.data.repository.FetchDetailsImpl
import com.benrostudios.apptitudeadmin.ui.auth.verification.VerificationViewModelFactory
import com.benrostudios.apptitudeadmin.ui.home.participants.ParticipantViewModelFactory
import com.benrostudios.apptitudeadmin.ui.home.teams.TeamsViewModelFactoy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ApptitudeAdmin : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@ApptitudeAdmin))
        bind<FetchDetails>() with singleton {FetchDetailsImpl()}
        bind<AuthRepository>() with singleton {AuthRepositoryImpl()}
        bind() from provider { TeamsViewModelFactoy(instance()) }
        bind() from provider { ParticipantViewModelFactory(instance()) }
        bind() from provider { VerificationViewModelFactory(instance()) }
    }
}