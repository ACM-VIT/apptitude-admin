package com.benrostudios.apptitudeadmin.data.models

data class AdminPanel(
    val allowProblemStatementGeneration: Boolean,
    val submissionDeadline: Long,
    val submissionBegin: Long,
    val discordLink: String
){
    constructor():this(false,0,0,"")
}