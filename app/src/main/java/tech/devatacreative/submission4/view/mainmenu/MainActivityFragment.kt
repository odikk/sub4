package tech.devatacreative.submission4.view.mainmenu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_match.view.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh
import tech.devatacreative.submission4.API.ApiRepository
import tech.devatacreative.submission4.API.ObjectAPI
import tech.devatacreative.submission4.CoroutineContextProvider
import tech.devatacreative.submission4.DB.Favorite
import tech.devatacreative.submission4.Model.MatchItem
import tech.devatacreative.submission4.R
import tech.devatacreative.submission4.view.detail.DetailActivity
import tech.devatacreative.submission4.view.favorites.FavoritesAdapter
import tech.devatacreative.submission4.utils.*


class MainActivityFragment: Fragment(), MainMenuInterface {
    private lateinit var listMatch: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var match: MutableList<MatchItem> = mutableListOf()
    private lateinit var matchPresenter: MainMenuPresenter
    private lateinit var matchAdapter: MainMenuAdapter
    private lateinit var views: View

    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var favoritesAdapter: FavoritesAdapter

    companion object {
        fun newInstance(): MainActivityFragment {
            val lastMatchFragment = MainActivityFragment()
            val args = Bundle()
            lastMatchFragment.arguments = args
            return lastMatchFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        views = inflater.inflate(R.layout.fragment_match, container, false)

        matchAdapter = MainMenuAdapter(ctx, match){
            startActivity(intentFor<(DetailActivity)>("id" to it.idEvent))
        }
        favoritesAdapter = FavoritesAdapter(ctx, favorites){
            startActivity(intentFor<DetailActivity>("id" to it.matchId ))
        }
        val apiRequest = ApiRepository()
        val gson = Gson()
        val APIObject = ObjectAPI
        matchPresenter = MainMenuPresenter(this, APIObject, apiRequest, gson, ctx, CoroutineContextProvider() )

        listMatch = views.rv_match_list
        listMatch.layoutManager = LinearLayoutManager(context)
        swipeRefreshLayout = views.swiperefresh
        progressBar = views.pb_home

        loading()
        swipeRefresh()

        return views

    }

    private fun swipeRefresh(){
        if(tag == "nextEvent") {
            listMatch.adapter = matchAdapter
            swipeRefreshLayout.onRefresh { matchPresenter.getNextMatch() }
        } else if (tag == "lastEvent") {
            listMatch.adapter = matchAdapter
            swipeRefreshLayout.onRefresh { matchPresenter.getLastMatch() }
        } else {
            listMatch.adapter = favoritesAdapter
            swipeRefreshLayout.onRefresh { matchPresenter.getFavorite() }
        }
    }

    private fun loading(){
        if (tag == "nextEvent"){
            listMatch.adapter = matchAdapter
            matchPresenter.getNextMatch()
        } else if (tag == "lastEvent") {
            listMatch.adapter = matchAdapter
            matchPresenter.getLastMatch()
        } else {
            listMatch.adapter = favoritesAdapter
            matchPresenter.getFavorite()
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showNextEvent(data: List<MatchItem>) {
        swipeRefreshLayout.isRefreshing = false
        match.clear()
        match.addAll(data)
        matchAdapter.notifyDataSetChanged()
    }

    override fun showLastEvent(data: List<MatchItem>) {
        swipeRefreshLayout.isRefreshing = false
        match.clear()
        match.addAll(data)
        matchAdapter.notifyDataSetChanged()
    }

    override fun showFavEvent(data: List<Favorite>) {
        swipeRefreshLayout.isRefreshing = false
        favorites.clear()
        favorites.addAll(data)
        favoritesAdapter.notifyDataSetChanged()
    }
}