package com.benrostudios.apptitudeadmin.ui.admin

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
import com.benrostudios.apptitudeadmin.utils.shortToaster
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.admin_execution_fragment.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class AdminExecution : BottomSheetDialogFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: AdminExecutionViewModelFactory by instance()
    private lateinit var executionCommand: String
    private lateinit var executionType: String
    private var longTime: Long = 0

    companion object {
        fun newInstance() = AdminExecution()
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
                heading = "Submission Deadline"; executionCommand = "submissionDeadline"; executionType = "long"
            }
            "event_status" -> {
                heading = "Event Status"; executionCommand = "allowProblemStatementGeneration"; executionType = "boolean"
            }
        }
        admin_execution_title.text = heading
        admin_execution_outline.hint = "Enter $heading"
        admin_execution_input.setText(value)
        admin_execution_button.setOnClickListener {
            if (admin_execution_input.text.toString().isNotEmpty()) {
                resultListener()
                executeOperation(executionType)
            }
        }
    }


    private fun executeOperation(type: String) {
        when (type) {
            "boolean" ->
                viewModel.executeOperation(
                    executionCommand,
                    admin_execution_input.text.toString().toLowerCase().startsWith("t")
                )

            "string" -> viewModel.executeOperation(
                executionCommand,
                admin_execution_input.text.toString()
            )

            "long" -> viewModel.executeOperation(executionCommand,longTime)
        }

    }

    private fun resultListener() {
        viewModel.result.observe(viewLifecycleOwner, Observer {
            if (it) {
                requireActivity().shortToaster("Value Changed Successfully!")
                dismiss()
            } else {
                requireActivity().shortToaster("Error in updating value!! , contact ACM App Dev Team")
            }
        })
    }

}