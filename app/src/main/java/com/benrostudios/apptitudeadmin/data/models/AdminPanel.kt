package com.benrostudios.apptitudeadmin.data.models

data class AdminPanel(
    val allowProblemStatementGeneration: String,
    val submissionDeadline: Long,
    val discordLink: String
){
    constructor():this("false",0,"")
}