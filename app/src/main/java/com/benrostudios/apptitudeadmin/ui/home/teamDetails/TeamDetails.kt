package com.benrostudios.apptitudeadmin.ui.home.teamDetails

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.adapters.ParticipantAdapter
import com.benrostudios.apptitudeadmin.data.models.Team
import com.benrostudios.apptitudeadmin.ui.base.ScopedFragment
import com.benrostudios.apptitudeadmin.ui.home.advanced.Advanced
import com.benrostudios.apptitudeadmin.ui.home.participantDetails.ParticipantDetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.team_details_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class TeamDetails : BottomSheetDialogFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: TeamDetailsViewModelFactory by instance()
    private lateinit var adapter: ParticipantAdapter

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
        val args = arguments
        val teamID = args?.getString("teamID")
        fetchTeams(teamID!!)
        listener()
        team_members_recycler_view.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun fetchTeams(id : String){
        viewModel.fetchTeamDetails(id)
    }
    private fun listener(){
        viewModel.teamDetails.observe(viewLifecycleOwner, Observer {
            Log.d("TeamDetails", "${it.membersDetails}")
            adapter = ParticipantAdapter(it.membersDetails,activity?.supportFragmentManager!!)
            populateUI(it)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun populateUI(team: Team){
        team_members_recycler_view.adapter = adapter
        team_name.text = team.name
        if(team.easy == "-1"){
            team_easy.text = "Not Generated"
            team_medium.text = "Not Generated"
            team_hard.text = "Not Generated"
        }else{
            team_easy.text = team.easy
            team_medium.text = team.medium
            team_hard.text = team.hard
        }
        team_github.text =if(team.githubLink=="-") "Not Submitted" else team.githubLink
        team_id.text = team.teamId
    }

}