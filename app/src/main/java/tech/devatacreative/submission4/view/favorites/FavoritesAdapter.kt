package tech.devatacreative.submission4.view.favorites

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_match_list.view.*
import tech.devatacreative.submission4.DB.Favorite
import tech.devatacreative.submission4.R
import java.text.SimpleDateFormat

class FavoritesAdapter(private val context: Context, private val match: List<Favorite>, private val listener: (Favorite) -> Unit)
    :RecyclerView.Adapter<FavoritesAdapter.MatchViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, position: Int) =
            MatchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_match_list, parent, false))

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindItem(match[position], listener)
    }

    override fun getItemCount(): Int = match.size


    class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(matches: Favorite, listener: (Favorite) -> Unit) {
            var dateFormat = SimpleDateFormat("yyyy-MM-dd")
            var date = dateFormat.parse(matches.matchDate)
            dateFormat = SimpleDateFormat("E, MMM-dd-yyyy")
            itemView.list_date.text = dateFormat.format(date)
            itemView.list_home_score.text = matches.teamHomeScore
            itemView.list_home_team.text = matches.teamHomeName
            itemView.list_away_team.text = matches.teamAwayName
            itemView.list_away_score.text = matches.teamAwayScore
            itemView.setOnClickListener {
                listener(matches)
            }
        }
    }
}