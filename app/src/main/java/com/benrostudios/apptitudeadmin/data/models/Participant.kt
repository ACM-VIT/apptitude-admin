package com.benrostudios.apptitudeadmin.data.models

import java.io.Serializable

data class Participant(
    val emailId: String,
    val name: String,
    val phoneNo: String,
    val teamId: String,
    var participantUid:String
): Serializable{
    constructor():this("","","","","")
}