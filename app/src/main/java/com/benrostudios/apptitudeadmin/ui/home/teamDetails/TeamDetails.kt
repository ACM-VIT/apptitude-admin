package com.benrostudios.apptitudeadmin.ui.home.teamDetails

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
import com.benrostudios.apptitudeadmin.ui.base.ScopedFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class TeamDetails : BottomSheetDialogFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: TeamDetailsViewModelFactory by instance()

    companion object {
        fun newInstance() = TeamDetails()
    }

    private lateinit var viewModel: TeamDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory).get(TeamDetailsViewModel::class.java)
        // TODO: Use the ViewModel
        fetchTeams("1553147172")
        listener()
    }

    private fun fetchTeams(id : String){
        viewModel.fetchTeamDetails(id)
    }
    private fun listener(){
        viewModel.teamDetails.observe(viewLifecycleOwner, Observer {
            Log.d("TeamDetails", "$it")
        })
    }

}