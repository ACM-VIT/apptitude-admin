package com.benrostudios.apptitudeadmin.ui.auth.userotp

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.utils.isValidMobile
import kotlinx.android.synthetic.main.user_otp_fragment.*

class UserOtp : Fragment() {

    companion object {
        fun newInstance() = UserOtp()
    }

    private lateinit var viewModel: UserOtpViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_otp_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserOtpViewModel::class.java)
        phone_cont_btn.setOnClickListener {
            if(phone_input.text?.isValidMobile() == true){
                val bundle = Bundle()
                bundle.putString("phoneNumber",phone_input.text.toString())
                navController.navigate(R.id.action_userOtp_to_verification,bundle)
            }else{
                phone_input.error = "Enter Valid Mobile Number"
            }

        }
    }

}