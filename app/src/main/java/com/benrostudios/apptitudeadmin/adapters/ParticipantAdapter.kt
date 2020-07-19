package com.benrostudios.apptitudeadmin.adapters


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.data.models.Participant
import com.benrostudios.apptitudeadmin.ui.home.participantDetails.ParticipantDetails
import com.google.android.gms.dynamic.SupportFragmentWrapper
import kotlinx.android.synthetic.main.participant_item.view.*

class ParticipantAdapter(
    private var participantsList: List<Participant>,
    private var supportFragmentManager: FragmentManager
) : RecyclerView.Adapter<ParticipantAdapter.ParticipantViewHolder>(),
    Filterable {

    private var mParticipantList: List<Participant> = participantsList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.participant_item, parent, false)
        return ParticipantViewHolder(view)
    }

    override fun getItemCount(): Int = participantsList.size

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        holder.name.text = participantsList[position].name
        holder.phone.text = participantsList[position].phoneNo
        holder.card.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("currentParticipant", participantsList[position])
            val participantSheet = ParticipantDetails()
            participantSheet.arguments = bundle
            participantSheet.show(
                supportFragmentManager,
                participantSheet.tag
            )
        }
    }

    class ParticipantViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = v.participant_item_name
        val phone: TextView = v.participant_item_phone
        val card: CardView = v.paritcipant_item_card
    }

    override fun getFilter(): Filter {
        return customFilter
    }

    private var customFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val queryString = constraint?.toString()?.toLowerCase()

            val filterResults = Filter.FilterResults()
            filterResults.values = if (queryString==null || queryString.isEmpty())
                mParticipantList
            else
                participantsList.filter {
                    it.name.toLowerCase().contains(queryString) ||
                            it.phoneNo.toLowerCase().contains(queryString) ||
                            it.emailId.toLowerCase().contains(queryString)
                }
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            participantsList = results?.values as List<Participant>
            notifyDataSetChanged()
        }

    }
}