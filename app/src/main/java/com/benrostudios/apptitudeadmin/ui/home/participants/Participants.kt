package com.benrostudios.apptitudeadmin.ui.home.participants

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
import com.benrostudios.apptitudeadmin.ui.base.ScopedFragment
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
    }

    fun fetchParticipants() = launch {
        viewModel.fetchParticipants()
        viewModel.participantsList.observe(viewLifecycleOwner, Observer {
            Log.d("Participants", "$it")
            adapter = ParticipantAdapter(it)
            participants_recyclerView.adapter = adapter
        })
    }

}