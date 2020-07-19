package com.benrostudios.apptitudeadmin.ui.admin.adminExecution

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.ui.home.advanced.Advanced.Companion.SELECTED_TITLE
import com.benrostudios.apptitudeadmin.ui.home.advanced.Advanced.Companion.SELECTED_VALUE
import com.benrostudios.apptitudeadmin.utils.hide
import com.benrostudios.apptitudeadmin.utils.shortToaster
import com.benrostudios.apptitudeadmin.utils.show
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.admin_execution_fragment.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*


class AdminExecution : BottomSheetDialogFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: AdminExecutionViewModelFactory by instance()
    private lateinit var executionCommand: String
    private lateinit var executionType: String
    private var longTime: Long = 0


    companion object {
        fun newInstance() =
            AdminExecution()
    }

    private lateinit var viewModel: AdminExecutionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.admin_execution_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val args = arguments
        val title = args?.getString(SELECTED_TITLE)
        val value = args?.getString(SELECTED_VALUE)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(AdminExecutionViewModel::class.java)
        var heading = ""
        when (title) {
            "discord" -> {
                heading = "Discord"; executionCommand = "discordLink"; executionType = "string"
            }
            "submission_deadline" -> {
                heading = "Submission Deadline"; executionCommand =
                    "submissionDeadline"; executionType = "long"
            }
            "event_status" -> {
                heading = "Event Status"; executionCommand =
                    "allowProblemStatementGeneration"; executionType = "boolean"
            }
            "submission_begin" -> {
                heading = "Submission Begin"; executionCommand =
                    "submissionBegin"; executionType = "long"
            }
        }
        if (executionType == "long") {
            admin_execution_input.isFocusable = false
            admin_execution_input.setOnClickListener {
                datePicker()
            }
        }
        admin_execution_title.text = heading
        admin_execution_outline.hint = "Enter $heading"
        admin_execution_input.setText(value)

        admin_execution_button.setOnClickListener {
            if (admin_execution_input.text.toString().isNotEmpty() && admin_execution_input.text.toString() != value) {
                admin_execution_progressbar.show()
                resultListener()
                executeOperation(executionType)
            }else{
                admin_execution_input.error = "Please Update the Field"
            }
        }
    }


    private fun executeOperation(type: String) {
        when (type) {
            "boolean" ->
                viewModel.executeOperation<Boolean>(
                    executionCommand,
                    admin_execution_input.text.toString().toLowerCase().startsWith("t")
                )

            "string" -> viewModel.executeOperation<String>(
                executionCommand,
                admin_execution_input.text.toString()
            )

            "long" -> viewModel.executeOperation<Long>(executionCommand, longTime)
        }

    }

    private fun resultListener() {
        viewModel.result.observe(viewLifecycleOwner, Observer {
            if (it) {
                requireActivity().shortToaster("Value Changed Successfully!")
                dismiss()
            } else {
                admin_execution_progressbar.hide()
                requireActivity().shortToaster("Error in updating value!! , contact ACM App Dev Team")
            }
        })
    }

    private fun datePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        var timeInMilli: Long = 0
        val dpd = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                c.set(Calendar.MONTH, monthOfYear)
                c.set(Calendar.YEAR, year)
                timeInMilli = c.timeInMillis / 1000
            },
            year,
            month,
            day
        )
        dpd.show()
        dpd.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            dpd.dismiss()
            val mTimePicker = TimePickerDialog(
                requireContext(),
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    c.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    c.set(Calendar.MINUTE, minute)
                    timeInMilli = c.timeInMillis / 1000
                    longTime = timeInMilli
                    Log.d("timeInMill", "$timeInMilli")
                    admin_execution_input.setText("$timeInMilli")
                }, hour, minute, false
            )
            mTimePicker.show()
        }
    }

}