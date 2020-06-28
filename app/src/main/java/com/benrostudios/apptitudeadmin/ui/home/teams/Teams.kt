package com.benrostudios.apptitudeadmin.ui.home.teams

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.ui.base.ScopedFragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class Teams : ScopedFragment(),KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactoy: TeamsViewModelFactoy by instance()
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
        // TODO: Use the ViewModel
    }

}