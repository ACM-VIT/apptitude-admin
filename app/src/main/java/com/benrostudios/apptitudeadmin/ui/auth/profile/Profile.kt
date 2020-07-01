package com.benrostudios.apptitudeadmin.ui.auth.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.data.models.Admin
import com.benrostudios.apptitudeadmin.ui.base.ScopedFragment
import com.benrostudios.apptitudeadmin.ui.home.MainActivity
import com.benrostudios.apptitudeadmin.utils.SharedPrefsUtils
import com.benrostudios.apptitudeadmin.utils.shortToaster
import com.benrostudios.apptitudeadmin.utils.show
import com.google.android.gms.common.util.SharedPreferencesUtils
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class Profile : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory : ProfileViewModelFactory by instance()
    private val sharedPrefsUtils: SharedPrefsUtils by instance()

    companion object {
        fun newInstance() = Profile()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory).get(ProfileViewModel::class.java)

        profile_verify_btn.setOnClickListener {
            if(name_input.text?.isNotEmpty() == true){
                profile_progress.show()
                makeProfile()
                responseListener()
            }else{
                name_input.error = "Please enter a valid name"
            }
        }
    }

    fun makeProfile() = launch{
        val adminObj: Admin = Admin("2","${System.currentTimeMillis()/1000}",name_input.text.toString(),sharedPrefsUtils.retrieveMobile() ?: "Unknown")
        viewModel.registerUser(adminObj)
    }

    fun responseListener() = launch {
        viewModel.registerResponse.observe(viewLifecycleOwner, Observer {
            if(it){
                requireContext().shortToaster("Welcome Admin!")
                var intent = Intent(requireContext(),MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }else{
                requireContext().shortToaster("Unable to register Admin , contact ACM App Dev Team")
            }
        })
    }


}