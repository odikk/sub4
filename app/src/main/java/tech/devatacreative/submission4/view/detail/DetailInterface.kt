package tech.devatacreative.submission4.view.detail

import tech.devatacreative.submission4.Model.DetailsItem
import tech.devatacreative.submission4.Model.TeamsItem

interface DetailInterface {
    fun showDetailEvent(data : List<DetailsItem>)
    fun showDetailHomeTeam(data : TeamsItem?)
    fun showDetailAwayTeam(data : TeamsItem?)
}