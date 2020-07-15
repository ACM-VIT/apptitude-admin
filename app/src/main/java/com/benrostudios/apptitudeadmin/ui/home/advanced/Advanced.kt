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
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.data.models.AdminPanel
import com.benrostudios.apptitudeadmin.ui.admin.AdminExecution
import com.benrostudios.apptitudeadmin.ui.base.ScopedFragment
import com.benrostudios.apptitudeadmin.ui.home.teams.TeamsViewModelFactory
import kotlinx.android.synthetic.main.advanced_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.nio.BufferUnderflowException
import java.text.SimpleDateFormat

class Advanced : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: AdvancedViewModelFactory by instance()
    private lateinit var navController: NavController

    companion object {
        fun newInstance() = Advanced()
        const val SELECTED_TITLE = "title"
        const val SELECTED_VALUE = "value"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
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
        viewModel = ViewModelProvider(this, viewModelFactory).get(AdvancedViewModel::class.java)
        fetchAdminPanel()

        discord_cardView.setOnClickListener {
            callBottomSheet("discord", admin_discord_link.text.toString())
        }
        submission_cardView.setOnClickListener {
            callBottomSheet("submission_deadline", admin_submission_deadline.text.toString())
        }
        event_status_cardView.setOnClickListener {
            callBottomSheet(
                "event_status",
                if (admin_event_status.text.toString() == "Event Live!") "true" else "false"
            )
        }
        admin_notifications_cardView.setOnClickListener {
            navController.navigate(R.id.action_advanced_to_notificationFragment)
        }
    }

    fun fetchAdminPanel() = launch {
        viewModel.fetchAdminPanel()
        viewModel.adminPanelResult.observe(viewLifecycleOwner, Observer {
            Log.d("Advanced", "$it")
            populateUI(it)
        })
    }

    fun populateUI(adminPanel: AdminPanel) {
        admin_discord_link.text = adminPanel.discordLink
        admin_submission_deadline.text = dateConverter(adminPanel.submissionDeadline)
        admin_event_status.text =
            if (adminPanel.allowProblemStatementGeneration) "Event Live!" else "Event yet to start!"
    }

    fun dateConverter(unix: Long): String {
        val sdf = SimpleDateFormat("MM'/'dd'/'y hh:mm aa")
        return sdf.format(unix * 1000).toString()
    }

    fun callBottomSheet(name: String, value: String) {
        val bundle = Bundle()
        bundle.putString(SELECTED_TITLE, name)
        bundle.putString(SELECTED_VALUE, value)
        val bottomSheetFragment = AdminExecution()
        bottomSheetFragment.arguments = bundle
        bottomSheetFragment.show(
            activity?.supportFragmentManager!!,
            bottomSheetFragment.tag
        )
    }

}