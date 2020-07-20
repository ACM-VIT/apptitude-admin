package com.benrostudios.apptitudeadmin.ui.home.participants

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.adapters.ParticipantAdapter
import com.benrostudios.apptitudeadmin.ui.base.ScopedFragment
import com.benrostudios.apptitudeadmin.ui.home.stats.StatsFragment
import com.benrostudios.apptitudeadmin.utils.hide
import com.benrostudios.apptitudeadmin.utils.shortToaster
import com.benrostudios.apptitudeadmin.utils.show
import kotlinx.android.synthetic.main.advanced_fragment.*
import kotlinx.android.synthetic.main.participants_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class Participants : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: ParticipantViewModelFactory by instance()
    private lateinit var adapter: ParticipantAdapter
    private var participantCount = 0
    private var teamsCount = 0

    companion object {
        fun newInstance() = Participants()
    }

    private lateinit var viewModel: ParticipantsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.participants_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        participant_searchView.setQuery("",false)
        participant_searchView.setIconifiedByDefault(true)
        participant_searchView.isIconified = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ParticipantsViewModel::class.java)
        fetchParticipants()
        fetchTeams()
        participants_recyclerView.layoutManager = LinearLayoutManager(requireContext())
        participantSearchViewImplementation()
        stats_fab.setOnClickListener {
            requireContext().shortToaster("Please wait , data is loading ......")
        }
    }

    private fun fetchParticipants() = launch {
        viewModel.fetchParticipants()
        viewModel.participantsList.observe(viewLifecycleOwner, Observer {
            Log.d("Participants", "$it")
            adapter = ParticipantAdapter(it.toMutableList(), activity?.supportFragmentManager!!)
            participantCount = it.size
            participants_recyclerView.adapter = adapter
        })
    }

    private fun fetchTeams() = launch {
        viewModel.fetchTeams()
        viewModel.teamsList.observe(viewLifecycleOwner, Observer {
            teamsCount = it.size
            stats_fab.setOnClickListener {
                val statsFrag = StatsFragment()
                val bundle = Bundle()
                bundle.putString("participant_stats","$participantCount")
                bundle.putString("teams_stats","$teamsCount")
                statsFrag.arguments = bundle
                statsFrag.show(activity?.supportFragmentManager!!, statsFrag.tag)
            }
        })
    }

    private fun participantSearchViewImplementation(){
        participant_searchView.onActionViewCollapsed()
        participant_searchView.setOnCloseListener {
            participant_title.show()
            false
        }
        participant_searchView.setOnSearchClickListener {
            participant_title.hide()
        }
        participant_searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }

}