package com.benrostudios.apptitudeadmin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.data.models.Participant
import kotlinx.android.synthetic.main.participant_item.view.*

class ParticipantAdapter(private val participantsList: List<Participant>): RecyclerView.Adapter<ParticipantAdapter.ParticipantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.participant_item,parent,false)
        return ParticipantViewHolder(view)
    }

    override fun getItemCount(): Int = participantsList.size

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        holder.name.text = participantsList[position].name
        holder.phone.text = participantsList[position].phoneNo
    }

    class ParticipantViewHolder(v: View): RecyclerView.ViewHolder(v){
        val name: TextView = v.participant_item_name
        val phone: TextView = v.participant_item_phone
    }
}