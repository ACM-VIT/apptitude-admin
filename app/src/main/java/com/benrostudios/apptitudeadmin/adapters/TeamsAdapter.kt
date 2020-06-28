package com.benrostudios.apptitudeadmin.adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.data.models.Team
import kotlinx.android.synthetic.main.teams_item.view.*

class TeamsAdapter(private val teamsList: List<Team>): RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.teams_item,parent,false)
        return TeamsViewHolder(view)
    }

    override fun getItemCount(): Int = teamsList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.teamName.text = teamsList[position].name
        holder.members.text = "Members: ${teamsList[position].members.size}"
    }

    class TeamsViewHolder(v: View): RecyclerView.ViewHolder(v){
        val teamName: TextView = v.teams_item_name
        val members: TextView = v.teams_item_members
    }
}