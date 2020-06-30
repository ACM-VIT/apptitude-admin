package com.benrostudios.apptitudeadmin.data.repository

import androidx.lifecycle.MutableLiveData
import com.benrostudios.apptitudeadmin.data.models.Notification
import com.benrostudios.apptitudeadmin.data.models.Status
import com.google.firebase.database.FirebaseDatabase

class PushDetails private constructor() {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val N_PATH = "/adminControl/notifications"
    fun pushNotification(notification: Notification?): MutableLiveData<Status> {
        val status =
            MutableLiveData<Status>()
        database.getReference(N_PATH).push().setValue(notification)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    status.setValue(Status.SUCCESS)
                } else {
                    status.setValue(Status.FAILED)
                }
            }
        return status
    }

    companion object {
        var instance: PushDetails? = null
            get() {
                if (field == null) {
                    field = PushDetails()
                }
                return field
            }
            private set
    }

}