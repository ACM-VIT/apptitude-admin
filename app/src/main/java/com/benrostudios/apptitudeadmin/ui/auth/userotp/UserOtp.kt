package com.benrostudios.apptitudeadmin.ui.auth.userotp

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benrostudios.apptitudeadmin.R

class UserOtp : Fragment() {

    companion object {
        fun newInstance() = UserOtp()
    }

    private lateinit var viewModel: UserOtpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_otp_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UserOtpViewModel::class.java)
        // TODO: Use the ViewModel
    }

}