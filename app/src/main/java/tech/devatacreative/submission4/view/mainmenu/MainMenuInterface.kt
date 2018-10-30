package tech.devatacreative.submission4.view.mainmenu

import tech.devatacreative.submission4.DB.Favorite
import tech.devatacreative.submission4.Model.MatchItem

interface MainMenuInterface {
    fun showLoading()
    fun hideLoading()
    fun showLastEvent(data : List<MatchItem>)
    fun showNextEvent(data : List<MatchItem>)
    fun showFavEvent(data: List<Favorite>)
}