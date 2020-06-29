package com.benrostudios.apptitudeadmin.ui.home.participants

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.adapters.ParticipantAdapter
import com.benrostudios.apptitudeadmin.ui.base.ScopedFragment
import com.benrostudios.apptitudeadmin.utils.hide
import com.benrostudios.apptitudeadmin.utils.show
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ParticipantsViewModel::class.java)
        fetchParticipants()
        participants_recyclerView.layoutManager = LinearLayoutManager(requireContext())
        participantSearchViewImplementation()
    }

    fun fetchParticipants() = launch {
        viewModel.fetchParticipants()
        viewModel.participantsList.observe(viewLifecycleOwner, Observer {
            Log.d("Participants", "$it")
            adapter = ParticipantAdapter(it.toMutableList())
            participants_recyclerView.adapter = adapter
        })
    }

    fun participantSearchViewImplementation(){
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