package com.benrostudios.apptitudeadmin.adapters


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.MediaController
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.benrostudios.apptitudeadmin.R
import com.benrostudios.apptitudeadmin.data.models.Participant
import com.benrostudios.apptitudeadmin.data.models.Team
import com.benrostudios.apptitudeadmin.ui.home.teamDetails.TeamDetails
import com.benrostudios.apptitudeadmin.ui.home.teamDetails.TeamDetailsViewModel
import kotlinx.android.synthetic.main.participant_details_fragment.*
import kotlinx.android.synthetic.main.teams_item.view.*

class TeamsAdapter(private var teamsList: List<Team>): RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>(),Filterable {

    private lateinit var mContext: Context
    private var mTeamsList: List<Team> = teamsList
    private lateinit var navController: NavController
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.teams_item, parent, false)
        return TeamsViewHolder(view)
    }

    override fun getItemCount(): Int = teamsList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.teamName.text = teamsList[position].name
        holder.members.text = if(teamsList[position].members.contains("-")) "Members: 1" else "Members: 2"
        holder.teamCard.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("teamID",teamsList[position].teamId)
            navController = Navigation.findNavController(it)
            navController.navigate(R.id.action_teams_to_teamDetails, bundle)
        }
    }

    override fun getFilter(): Filter {
        return customFilter
    }

    private var customFilter: Filter = object : Filter() {
        var tempList: MutableList<Team> = mutableListOf()
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val queryString = constraint?.toString()?.toLowerCase()

            val filterResults = Filter.FilterResults()
            filterResults.values = if (queryString == null || queryString.isEmpty())
                mTeamsList
            else {
                teamsList.filter {
                    it.name.toLowerCase().contains(queryString) ||
                            it.githubLink.toLowerCase().contains(queryString) ||
                            it.members.contains(queryString)

                }
            }
            return filterResults
        }




        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            teamsList = results?.values as List<Team>
            notifyDataSetChanged()
        }

    }

    class TeamsViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val teamName: TextView = v.teams_item_name
        val members: TextView = v.teams_item_members
        val teamCard = v.team_item_view_holder
    }
}