package tech.devatacreative.submission4.view.detail

import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import tech.devatacreative.submission4.API.ApiRepository
import tech.devatacreative.submission4.API.ObjectAPI
import tech.devatacreative.submission4.CoroutineContextProvider
import tech.devatacreative.submission4.Model.DetailsResponse
import tech.devatacreative.submission4.Model.TeamsResponse

class DetailPresenter(private val view: DetailInterface,
                      private val apiRepository: ApiRepository,
                      private val objectApiRepository: ObjectAPI,
                      private val gson: Gson,
                      private val coroutineContext: CoroutineContextProvider = CoroutineContextProvider()){

    fun getLookupEvent(id: String){
        async(coroutineContext.main) {
            val data = bg{gson.fromJson(apiRepository
                    .doRequest(objectApiRepository.getLookupMatch(id)),
                    DetailsResponse::class.java)
            }
                view.showDetailEvent(data.await().events)
        }
    }

    fun getLookupTeam(id: String?, type: String?){
        async(coroutineContext.main) {
            val data = bg{ gson.fromJson(apiRepository
                    .doRequest(objectApiRepository.getLookupTeam(id)),
                    TeamsResponse::class.java)
            }
                if (type == "HOME"){
                    view.showDetailHomeTeam(data.await().teams?.get(0))
                } else {
                    view.showDetailAwayTeam(data.await().teams?.get(0))
                }
        }
    }
}