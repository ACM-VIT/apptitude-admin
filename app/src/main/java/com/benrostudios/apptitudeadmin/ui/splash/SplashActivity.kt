package com.benrostudios.apptitudeadmin.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.ui.auth.AuthActivity
import com.benrostudios.apptitudeadmin.ui.home.MainActivity
import com.benrostudios.apptitudeadmin.utils.SharedPrefsUtils
import com.google.firebase.auth.FirebaseAuth
import com.ieeevit.gakko.ui.splash.SplashViewModel
import com.ieeevit.gakko.ui.splash.SplashViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class SplashActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: SplashViewModelFactory by instance()
    private lateinit var viewModel: SplashViewModel
    private val utils: SharedPrefsUtils by instance()
    private lateinit var firebaseAuth: FirebaseAuth

    private val SPLASH_TIME_OUT = 1000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        viewModel = ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)
        firebaseAuth = FirebaseAuth.getInstance()
    }
    private fun initialize() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(
            {
                lifecycleScope.launchWhenCreated {
                    userCheck()
                }
            }, SPLASH_TIME_OUT
        )
    }

    private suspend fun userCheck() {
        val phone = utils.retrieveMobile()
        if (phone == null) {
            unAuthenticatedUser()
            Log.d("Splash","Unauthenticated User")
        } else {
            viewModel.getUser(phone)
            viewModel.userResponse.observe(this, Observer {
                if (it) {
                    Log.d("Splash","Authenticated User")
                    authenticatedUser()
                } else {
                    Log.d("Splash","Form UnFilled Authenticated User")
                    firebaseAuth.signOut()
                    unAuthenticatedUser()
                }
            })
        }
    }

    private fun unAuthenticatedUser() {
        val i = Intent(this, AuthActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun authenticatedUser() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}