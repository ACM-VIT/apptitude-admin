package com.benrostudios.apptitudeadmin.ui.home.notification

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.data.models.Notification
import com.benrostudios.apptitudeadmin.data.models.Status
import kotlinx.android.synthetic.main.fragment_notification.*

class NotificationFragment : Fragment() {
    private var viewModel: NotificationViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(NotificationViewModel::class.java)
        viewModel!!.getPushStatus().observe(viewLifecycleOwner, Observer<Status> {
            if (it == Status.SUCCESS) {
                Toast.makeText(context, "Notification Sent", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    context,
                    "Error: Unable to send the Notification",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        title_notification.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                send_btn_notification.isEnabled = s.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        send_btn_notification?.setOnClickListener {
            val notification =
                Notification(
                    title_notification!!.text.toString(),
                    body_notification!!.text.toString()
                )
            viewModel?.pushNotification(notification)

            title_notification.setText("")
            body_notification.setText("")
            send_btn_notification.isEnabled = false

        }
    }

}