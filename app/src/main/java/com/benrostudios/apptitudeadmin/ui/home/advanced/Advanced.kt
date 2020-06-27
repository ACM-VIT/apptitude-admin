package com.benrostudios.apptitudeadmin.ui.home.advanced

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benrostudios.apptitudeadmin.R

class Advanced : Fragment() {

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
        viewModel = ViewModelProviders.of(this).get(AdvancedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}