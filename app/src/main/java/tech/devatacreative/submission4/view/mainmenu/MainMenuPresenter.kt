package tech.devatacreative.submission4.view.mainmenu

import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import tech.devatacreative.submission4.API.ApiRepository
import tech.devatacreative.submission4.API.ObjectAPI
import tech.devatacreative.submission4.CoroutineContextProvider
import tech.devatacreative.submission4.DB.Favorite
import tech.devatacreative.submission4.DB.database
import tech.devatacreative.submission4.Model.MatchResponse
import tech.devatacreative.submission4.test.TestContextProvider

class MainMenuPresenter(
    private val view: MainMenuInterface,
    private val apiObject: ObjectAPI,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: Context,
    private val coroutineContext: CoroutineContextProvider = TestContextProvider()
    ){
    fun getLastMatch() {
        view.showLoading()
        async(coroutineContext.main) {
            val data = bg{
                gson.fromJson(apiRepository
                        .doRequest(apiObject.getLastMatch()),
                        MatchResponse::class.java)
            }

            view.hideLoading()
            view.showLastEvent(data.await().events)
        }
    }

    fun getNextMatch(){
        view.showLoading()
        async(coroutineContext.main) {
            val data = bg{gson.fromJson(apiRepository
                    .doRequest(apiObject.getNextMatch()),
                    MatchResponse::class.java)
            }
            view.hideLoading()
            view.showNextEvent(data.await().events)
        }
    }
    fun getFavorite(){
        view.showLoading()

        context.database.use {
            val favorite = select(Favorite.TABLE_FAVORITE)
            val list = favorite.parseList(classParser<Favorite>())
            view.hideLoading()
            view.showFavEvent(list)
        }

    }


}

