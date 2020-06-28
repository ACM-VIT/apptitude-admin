package com.benrostudios.apptitudeadmin.ui.home.teams


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.adapters.TeamsAdapter
import com.benrostudios.apptitudeadmin.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.teams_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class Teams : ScopedFragment(),KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactoy: TeamsViewModelFactoy by instance()
    private lateinit var adapter: TeamsAdapter
    companion object {
        fun newInstance() = Teams()
    }

    private lateinit var viewModel: TeamsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.teams_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactoy).get(TeamsViewModel::class.java)
        fetchTeams()
        teams_recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun fetchTeams() = launch {
        viewModel.fetchTeams()
        viewModel.teamsList.observe(viewLifecycleOwner, Observer {
            Log.d("teams","$it")
            adapter = TeamsAdapter(it)
            teams_recyclerView.adapter = adapter
        })
    }

}