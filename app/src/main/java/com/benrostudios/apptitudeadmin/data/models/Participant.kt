package com.benrostudios.apptitudeadmin.data.models

data class Participant(
    val emailId: String,
    val name: String,
    val phoneNo: String,
    val teamId: String
){
    constructor():this("","","","")
}