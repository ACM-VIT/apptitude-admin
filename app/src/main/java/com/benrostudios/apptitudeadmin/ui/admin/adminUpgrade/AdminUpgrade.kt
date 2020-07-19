package com.benrostudios.apptitudeadmin.ui.admin.adminUpgrade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.utils.SharedPrefsUtils
import com.benrostudios.apptitudeadmin.utils.errSnack
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.advanced_fragment.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class AdminUpgrade : BottomSheetDialogFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory : AdminUpgradeViewModelFactory by instance()
    private val sharedPrefsUtils: SharedPrefsUtils by instance()

    companion object {
        fun newInstance() = AdminUpgrade()
    }

    private lateinit var viewModel: AdminUpgradeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.admin_upgrade_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AdminUpgradeViewModel::class.java)
        listen()
    }

    private fun requestIncrementation(){
        val phone = sharedPrefsUtils.retrieveMobile() ?: ""
        viewModel.upgradeAdmin(phone , 0)
    }

    private fun listen(){
        viewModel.getAdminIncrementationStatus.observe(viewLifecycleOwner, Observer {
            if(it){
                sharedPrefsUtils.saveAdminLevel(0)
                upgrade_admin_btn.errSnack("Success , you are a privileged admin now!")
            }else{
                upgrade_admin_btn.errSnack("Failure to upgrade, contact AppDept Lead",Snackbar.LENGTH_LONG)
            }
            dismiss()
        })
    }


}