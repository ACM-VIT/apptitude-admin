package com.benrostudios.apptitudeadmin.ui.auth.verification

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.ui.base.ScopedFragment
import com.benrostudios.apptitudeadmin.ui.home.MainActivity
import com.benrostudios.apptitudeadmin.utils.SharedPrefsUtils
import com.benrostudios.apptitudeadmin.utils.hide
import com.benrostudios.apptitudeadmin.utils.shortToaster
import com.benrostudios.apptitudeadmin.utils.show
import com.google.android.gms.common.util.SharedPreferencesUtils
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.user_otp_fragment.*
import kotlinx.android.synthetic.main.verification_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.concurrent.TimeUnit

class Verification : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: VerificationViewModelFactory by instance()
    private val utils: SharedPrefsUtils by instance()
    private var verificationInProgress: Boolean = false
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private var verificationToken: String? = null
    private lateinit var phoneNumber: String
    private lateinit var navController: NavController
    private val VERIFICATION_FRAG = "verificationfrag"

    companion object {
        fun newInstance() = Verification()
    }

    private lateinit var viewModel: VerificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.verification_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(VerificationViewModel::class.java)
        if (savedInstanceState != null) {
            verificationInProgress = savedInstanceState.getBoolean(VERIFICATION_FRAG)
        }
        if(!verificationInProgress) {
            initiateSignIn(phoneNumber)
        }
        otp_verify_btn.setOnClickListener {
            getResponse()
            if (verificationToken != null) {
                verifyPhoneNumberWithCode(otp_input.text.toString())
                verfication_progress.show()
            }
        }
    }
    private fun initiateSignIn(phoneNumber: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            requireActivity(),
            callbacks
        )
        verificationInProgress = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        phoneNumber = arguments?.getString("phoneNumber") ?: ""
        verification_phone_display.text = "OTP has been sent to : $phoneNumber"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(VERIFICATION_FRAG, verificationInProgress)
    }
    private fun verifyPhoneNumberWithCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationToken!!, code)
        signInWithFirebase(credential)
    }

    private fun signInWithFirebase(credential: PhoneAuthCredential) = launch {
        viewModel.signInWithFirebase(credential)
    }


    var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            Log.d(VERIFICATION_FRAG, "onVerificationCompleted:$credential")

            signInWithFirebase(credential)

        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(VERIFICATION_FRAG, "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                // ...
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // ...
            }

        }
        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(VERIFICATION_FRAG, "onCodeSent:$verificationId")

            // Save verification ID and resending token so we can use them later
            verificationToken = verificationId
            resendToken = token


        }
    }

    private fun getResponse() = launch {
        viewModel.authResponse.observe(viewLifecycleOwner, Observer {
            if(it){
                utils.saveMobile(phoneNumber)
                utils.saveAdminLevel(2)
                navController.navigate(R.id.action_verification_to_profile)

            }else{
                Snackbar.make(otp_verify_btn,"Verification Failure",Snackbar.LENGTH_LONG).show()
                verfication_progress.hide()
            }
        })
    }



}