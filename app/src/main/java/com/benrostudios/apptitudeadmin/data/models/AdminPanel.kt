package com.benrostudios.apptitudeadmin.data.models

data class AdminPanel(
    val allowProblemStatementGeneration: Boolean,
    val submissionDeadline: String,
    val discordLink: String
){
    constructor():this(false,"","")
}