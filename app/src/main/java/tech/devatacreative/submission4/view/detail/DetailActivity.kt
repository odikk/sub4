package tech.devatacreative.submission4.view.detail

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import tech.devatacreative.submission4.API.ApiRepository
import tech.devatacreative.submission4.API.ObjectAPI
import tech.devatacreative.submission4.DB.Favorite
import tech.devatacreative.submission4.DB.database
import tech.devatacreative.submission4.Model.*
import tech.devatacreative.submission4.R
import tech.devatacreative.submission4.R.drawable.ic_add_to_favorites
import tech.devatacreative.submission4.R.drawable.ic_added_to_favorites
import tech.devatacreative.submission4.R.menu.detail_menu
import java.text.SimpleDateFormat

class DetailActivity : AppCompatActivity(),  DetailInterface{

    private lateinit var detailPresenter: DetailPresenter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var match: DetailsItem
    private var favoriteMenu: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.title = "Match detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getStringExtra("id")
        val request = ApiRepository()
        val gson = Gson()
        val objects = ObjectAPI

        detailPresenter = DetailPresenter(this, request, objects, gson)
        detailPresenter.getLookupEvent(id)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        favoriteMenu = menu?.findItem(R.id.add_to_favorite)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) {
                    removeFromFavorite()
                } else {

                    addToFavorite()
                }
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun addToFavorite(){
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                        Favorite.MATCH_ID to match.idEvent.toString(),
                        Favorite.MATCH_DATE to match.dateEvent,
                        Favorite.TEAM_HOME_NAME to match.strHomeTeam.toString(),
                        Favorite.TEAM_AWAY_NAME to match.strAwayTeam.toString(),
                        Favorite.TEAM_HOME_SCORE to match.intHomeScore?.toString(),
                        Favorite.TEAM_AWAY_SCORE to match.intAwayScore?.toString())
            }
            detail_layout.snackbar("Match Added to Favorite").show()
        } catch (e: SQLiteConstraintException){
            detail_layout.snackbar(e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(MATCH_ID = {id})",
                        "id" to match.idEvent.toString())
            }
            detail_layout.snackbar("Match Removed from Favorite").show()
        } catch (e: SQLiteConstraintException){
            detail_layout.snackbar(e.localizedMessage).show()
        }
    }

    private fun setFavorite(){
        if (isFavorite){
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        } else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
        }
    }

    private fun favoriteState(){

        database.use {
            val result = select(Favorite.TABLE_FAVORITE).whereArgs("(MATCH_ID) = {id}", "id" to match.idEvent.toString())
            val fav = result.parseList(classParser<Favorite>())
            if (!fav.isEmpty()) {
                isFavorite = true
            }
            setFavorite()
        }
    }



    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
    override fun showDetailEvent(data: List<DetailsItem>) {
        val item = data.get(0)
        match = data.get(0)


        var dateFormat = SimpleDateFormat("yyyy-MM-dd")
        var date = dateFormat.parse(item.dateEvent)
        dateFormat = SimpleDateFormat("EEE, dd MMMM yyyy")

        tvDate.text = dateFormat.format(date).toString()

        tvHomeTeam.text = item.strHomeTeam
        tvAwayTeam.text = item.strAwayTeam

        homeShots.text = item.intHomeShots
        awayShots.text = item.intAwayShots

        homeGoalDetail.text = item.strHomeGoalDetails?.replace(";","\n")
        awayGoalDetail.text = item.strAwayGoalDetails?.replace(";","\n")

        homeGoalKeeper.text = item.strHomeLineupGoalkeeper?.replace("; ","\n")
        awayGoalKeeper.text = item.strAwayLineupGoalkeeper?.replace("; ","\n")


        homeDefense.text = item.strHomeLineupDefense?.replace("; ","\n")
        awayDefense.text = item.strAwayLineupDefense?.replace("; ","\n")


        homeMidfield.text = item.strHomeLineupMidfield?.replace("; ","\n")
        awayMidfield.text = item.strAwayLineupMidfield?.replace("; ","\n")


        homeForward.text = item.strHomeLineupForward?.replace("; ","\n")
        awayForward.text = item.strAwayLineupForward?.replace("; ","\n")


        homeSubstitution.text = item.strHomeLineupSubstitutes?.replace("; ","\n")
        awaySubstitution.text = item.strAwayLineupSubstitutes?.replace("; ","\n")

        detailPresenter.getLookupTeam(item.idAwayTeam,"AWAY")
        detailPresenter.getLookupTeam(item.idHomeTeam,"HOME")
        favoriteState()
        favoriteMenu?.isVisible = true

    }

    override fun showDetailHomeTeam(data: TeamsItem?) {
        Picasso.get().load(data?.strTeamBadge).into(imgHomeLogo)
    }

    override fun showDetailAwayTeam(data: TeamsItem?) {
        Picasso.get().load(data?.strTeamBadge).into(imgAwayLogo)
    }

}
