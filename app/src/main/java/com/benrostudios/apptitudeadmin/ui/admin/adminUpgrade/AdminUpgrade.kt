package com.benrostudios.apptitudeadmin.ui.admin.adminUpgrade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.utils.*
import com.benrostudios.apptitudeadmin.utils.Constants.Companion.ROOT_PASSWORD
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.admin_upgrade_fragment.*
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
        cur_admin_level_display.text = "${sharedPrefsUtils.retrieveAdminLevel()}"
        upgrade_admin_privileges_btn.setOnClickListener {
            if(admin_upgrade_input.isValidAlphaNumeric()){
                if(admin_upgrade_input.text.toString() == ROOT_PASSWORD){
                    requestIncrementation()
                    progressBarAdminPrivilege.show()
                }else{
                    admin_upgrade_outline.error = "Not a valid password!"
                }
            }else{
                admin_upgrade_outline.error = "Invalid Password Format!"
            }
        }
    }

    private fun requestIncrementation(){
        val phone = sharedPrefsUtils.retrieveMobile() ?: ""
        viewModel.upgradeAdmin(phone , 0)
    }

    private fun listen(){
        viewModel.getAdminIncrementationStatus.observe(viewLifecycleOwner, Observer {
            if(it){
                sharedPrefsUtils.saveAdminLevel(0)
                requireContext().shortToaster("Success , you are a privileged admin now!")
                dismiss()
            }else{
                requireContext().shortToaster("Failure to upgrade!, contact AppDept Lead")
                progressBarAdminPrivilege.hide()
            }

        })
    }


}