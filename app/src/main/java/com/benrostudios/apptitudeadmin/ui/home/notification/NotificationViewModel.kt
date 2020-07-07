package com.benrostudios.apptitudeadmin.ui.home.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.benrostudios.apptitudeadmin.data.models.Notification
import com.benrostudios.apptitudeadmin.data.models.Status
import com.benrostudios.apptitudeadmin.data.repository.PushDetails

class NotificationViewModel(
) : ViewModel() {
    private val repository: PushDetails? = PushDetails.instance
    private val pushStatus : MediatorLiveData<Status> = MediatorLiveData()

    fun getPushStatus() : LiveData<Status>{
        return pushStatus
    }

    fun pushNotification(notification: Notification?) {
        val statusLiveData: LiveData<Status> =
            repository?.pushNotification(notification) as LiveData<Status>

        pushStatus.addSource(
            statusLiveData
        ) { status ->
            pushStatus.value = status
            pushStatus.removeSource(
                statusLiveData
            )
        }
    }

}