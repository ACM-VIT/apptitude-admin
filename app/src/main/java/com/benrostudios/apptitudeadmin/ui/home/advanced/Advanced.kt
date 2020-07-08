package com.benrostudios.apptitudeadmin.ui.home.advanced

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.data.models.AdminPanel
import com.benrostudios.apptitudeadmin.ui.base.ScopedFragment
import com.benrostudios.apptitudeadmin.ui.home.teams.TeamsViewModelFactory
import kotlinx.android.synthetic.main.advanced_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat

class Advanced : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: AdvancedViewModelFactory by instance()

    companion object {
        fun newInstance() = Advanced()
    }

    private lateinit var viewModel: AdvancedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.advanced_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory).get(AdvancedViewModel::class.java)
        fetchAdminPanel()
    }

    fun fetchAdminPanel() = launch{
        viewModel.fetchAdminPanel()
        viewModel.adminPanelResult.observe(viewLifecycleOwner, Observer {
            Log.d("Advanced","$it")
            populateUI(it)
        })
    }

    fun populateUI(adminPanel: AdminPanel){
        admin_discord_link.text = adminPanel.discordLink
        admin_submission_deadline.text = dateConverter(adminPanel.submissionDeadline)
        admin_event_status.text = if(adminPanel.allowProblemStatementGeneration) "Event Live!" else "Event yet to start!"
    }

    fun dateConverter(unix: Long): String{
        val sdf = SimpleDateFormat("MM'/'dd'/'y hh:mm aa")
        return sdf.format(unix*1000).toString()
    }

}