package com.benrostudios.apptitudeadmin.data.models

data class Team(
    val easy: String,
    val hard: String,
    val medium: String,
    val name: String,
    val githubLink: String,
    val members: List<String>
){
    constructor():this("","","","","", emptyList())
}