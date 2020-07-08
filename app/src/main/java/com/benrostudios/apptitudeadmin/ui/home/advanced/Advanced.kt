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
import com.benrostudios.apptitudeadmin.ui.base.ScopedFragment
import com.benrostudios.apptitudeadmin.ui.home.teams.TeamsViewModelFactory
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

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
        })
    }

}