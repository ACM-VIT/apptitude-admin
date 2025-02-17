package com.benrostudios.apptitudeadmin

import android.app.Application
import com.benrostudios.apptitudeadmin.data.repository.*
import com.benrostudios.apptitudeadmin.ui.admin.adminExecution.AdminExecutionViewModelFactory
import com.benrostudios.apptitudeadmin.ui.admin.adminUpgrade.AdminUpgradeViewModelFactory
import com.benrostudios.apptitudeadmin.ui.auth.profile.ProfileViewModelFactory
import com.benrostudios.apptitudeadmin.ui.auth.verification.VerificationViewModelFactory
import com.benrostudios.apptitudeadmin.ui.home.advanced.AdvancedViewModelFactory
import com.benrostudios.apptitudeadmin.ui.home.participantDetails.ParticipantDetailsViewModelFactory
import com.benrostudios.apptitudeadmin.ui.home.participants.ParticipantViewModelFactory
import com.benrostudios.apptitudeadmin.ui.home.teamDetails.TeamDetailsViewModelFactory
import com.benrostudios.apptitudeadmin.ui.home.teams.TeamsViewModelFactory
import com.benrostudios.apptitudeadmin.utils.SharedPrefsUtils
import com.benrostudios.apptitudeadmin.ui.splash.SplashViewModelFactory
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
        bind() from singleton { SharedPrefsUtils(instance()) }
        bind<AuthRepository>() with singleton {AuthRepositoryImpl()}
        bind<AdminRepository>() with singleton {AdminRepositoryImpl()}
        bind<AdminPrivileges>() with singleton {AdminPrivilegesImpl()}
        bind() from provider { TeamsViewModelFactory(instance()) }
        bind() from provider { ParticipantViewModelFactory(instance()) }
        bind() from provider { VerificationViewModelFactory(instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider { SplashViewModelFactory(instance()) }
        bind() from provider { AdvancedViewModelFactory(instance()) }
        bind() from provider { AdminExecutionViewModelFactory(instance())}
        bind() from provider { TeamDetailsViewModelFactory(instance()) }
        bind() from provider { AdminUpgradeViewModelFactory(instance()) }
        bind() from provider { ParticipantDetailsViewModelFactory(instance()) }
    }
}