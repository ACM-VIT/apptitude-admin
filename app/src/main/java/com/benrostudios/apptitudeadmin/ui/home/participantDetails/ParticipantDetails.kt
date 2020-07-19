package com.benrostudios.apptitudeadmin.ui.home.participantDetails



import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.data.models.Participant
import com.benrostudios.apptitudeadmin.utils.SharedPrefsUtils
import com.benrostudios.apptitudeadmin.utils.shortToaster
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.participant_details_fragment.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class ParticipantDetails : BottomSheetDialogFragment(),KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: ParticipantDetailsViewModelFactory by instance()
    private val sharedPrefsUtils: SharedPrefsUtils by instance()


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
        viewModel = ViewModelProvider(this,viewModelFactory).get(ParticipantDetailsViewModel::class.java)
        populateUI()
        deleteParticipant.setOnClickListener {
           val adminLevel = sharedPrefsUtils.retrieveAdminLevel() ?: 2
            if(adminLevel < 1){
                showAlertDialog()
            }else{
                requireContext().shortToaster("You are not a privileged admin to perform this operation!")
            }
        }
    }

    private fun populateUI(){
        participant_detail_email.text = currentParticipant.emailId
        participant_detail_phone.text = currentParticipant.phoneNo
        participant_detail_name.text = currentParticipant.name
        enableClickers()
    }

    private fun enableClickers(){
        participant_detail_email?.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(participant_detail_email.text.toString()))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Feedback")
            if (emailIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(emailIntent)
            }else{
                requireActivity().shortToaster("No Email apps found!")
            }
        }
    }

    private fun nukeParticipant(){
        viewModel.removeParticipant(currentParticipant.participantUid)
        viewModel.removeStatus.observe(viewLifecycleOwner, Observer {
            if(it){
                requireContext().shortToaster("Removed Participant!")
                dismiss()
            }else{
                requireContext().shortToaster("Failed to remove participant!")
            }
        })
    }

    private fun showAlertDialog(){
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme )
        alertDialog.setTitle("CAUTION !")
        alertDialog.setMessage("Are you sure you want to delete ${currentParticipant.name} ?")
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert)
        alertDialog.setPositiveButton("Yes"){dialogInterface, which ->
                nukeParticipant()
        }
        alertDialog.setNeutralButton("Cancel"){dialogInterface , which ->
            requireContext().shortToaster("Process Cancelled")
        }
        val alertDialogDisplay: AlertDialog = alertDialog.create()
        alertDialogDisplay.show()

    }

}