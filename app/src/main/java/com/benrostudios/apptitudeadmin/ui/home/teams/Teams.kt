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
import com.benrostudios.apptitudeadmin.utils.hide
import com.benrostudios.apptitudeadmin.utils.show
import kotlinx.android.synthetic.main.teams_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class Teams : ScopedFragment(),KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: TeamsViewModelFactory by instance()
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
        viewModel = ViewModelProvider(this,viewModelFactory).get(TeamsViewModel::class.java)
        fetchTeams()
        teamsSearchViewImplementation()
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

    fun teamsSearchViewImplementation(){
        teams_searchView.onActionViewCollapsed()
        teams_searchView.setOnCloseListener {
            teams_title.show()
            false
        }
        teams_searchView.setOnSearchClickListener {
            teams_title.hide()
        }
        teams_searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
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