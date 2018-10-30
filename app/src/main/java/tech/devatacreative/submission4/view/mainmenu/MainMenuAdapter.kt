package tech.devatacreative.submission4.view.mainmenu

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_match_list.view.*
import tech.devatacreative.submission4.Model.MatchItem
import tech.devatacreative.submission4.R
import java.text.SimpleDateFormat

class MainMenuAdapter(private val context: Context, private val match: List<MatchItem>, private val listener: (MatchItem) -> Unit)
    :RecyclerView.Adapter<MainMenuAdapter.MatchViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, position: Int) =
            MatchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_match_list, parent, false))

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindItem(match[position], listener)
    }

    override fun getItemCount(): Int = match.size


    class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(matches: MatchItem, listener: (MatchItem) -> Unit) {
            var dateFormat = SimpleDateFormat("yyyy-MM-dd")
            var date = dateFormat.parse(matches.dateEvent)
            dateFormat = SimpleDateFormat("E, MMM dd yyyy")
            itemView.list_date.text = dateFormat.format(date)
            itemView.list_home_score.text = matches.intHomeScore
            itemView.list_home_team.text = matches.strHomeTeam
            itemView.list_away_team.text = matches.strAwayTeam
            itemView.list_away_score.text = matches.intAwayScore
            itemView.setOnClickListener {
                listener(matches)
            }
        }
    }
}