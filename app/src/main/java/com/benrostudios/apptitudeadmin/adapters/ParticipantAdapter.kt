package com.benrostudios.apptitudeadmin.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.data.models.Participant
import kotlinx.android.synthetic.main.participant_item.view.*
import java.io.Serializable

class ParticipantAdapter(private val participantsList: List<Participant>): RecyclerView.Adapter<ParticipantAdapter.ParticipantViewHolder>() {
    private lateinit var navController: NavController

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.participant_item,parent,false)
        return ParticipantViewHolder(view)
    }

    override fun getItemCount(): Int = participantsList.size

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        holder.name.text = participantsList[position].name
        holder.phone.text = participantsList[position].phoneNo
        holder.card.setOnClickListener {
            navController = Navigation.findNavController(it)
            val bundle = Bundle()
            bundle.putSerializable("currentParticipant", participantsList[position])
            navController.navigate(R.id.action_participants_to_participantDetails,bundle)
        }
    }

    class ParticipantViewHolder(v: View): RecyclerView.ViewHolder(v){
        val name: TextView = v.participant_item_name
        val phone: TextView = v.participant_item_phone
        val card: CardView = v.paritcipant_item_card
    }
}