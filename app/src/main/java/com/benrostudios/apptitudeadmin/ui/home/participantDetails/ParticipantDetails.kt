package com.benrostudios.apptitudeadmin.ui.home.participantDetails

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.data.models.Participant
import com.benrostudios.apptitudeadmin.ui.base.ScopedFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.participant_details_fragment.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

class ParticipantDetails : BottomSheetDialogFragment(),KodeinAware {

    override val kodein: Kodein by closestKodein()


    private lateinit var currentParticipant: Participant

    companion object {
        fun newInstance() = ParticipantDetails()
    }

    private lateinit var viewModel: ParticipantDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val extras = arguments?.getSerializable("currentParticipant")
        if (extras != null) {
            currentParticipant = extras as Participant
        }
        return inflater.inflate(R.layout.participant_details_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ParticipantDetailsViewModel::class.java)
        populateUI()
    }

    private fun populateUI(){
        participant_detail_email.text = currentParticipant.emailId
        participant_detail_phone.text = currentParticipant.phoneNo
        participant_detail_name.text = currentParticipant.name
    }

}