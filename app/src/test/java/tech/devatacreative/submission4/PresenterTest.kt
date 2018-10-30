package tech.devatacreative.submission4

import android.content.Context
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import tech.devatacreative.submission4.API.ApiRepository
import tech.devatacreative.submission4.API.ObjectAPI
import tech.devatacreative.submission4.Model.MatchItem
import tech.devatacreative.submission4.Model.MatchResponse
import tech.devatacreative.submission4.Model.TeamsItem
import tech.devatacreative.submission4.Model.TeamsResponse
import tech.devatacreative.submission4.view.detail.DetailInterface
import tech.devatacreative.submission4.view.detail.DetailPresenter
import tech.devatacreative.submission4.view.mainmenu.MainMenuInterface
import tech.devatacreative.submission4.view.mainmenu.MainMenuPresenter
import tech.devatacreative.submission4.test.TestContextProvider

class PresenterTest{
    @Mock
    private lateinit var mainView: MainMenuInterface

    @Mock
    private lateinit var detailView: DetailInterface

    @Mock
    private lateinit var apiObject: ObjectAPI

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var context: Context

    private lateinit var mainMenuPresenter: MainMenuPresenter
    private lateinit var detailPresenter: DetailPresenter
    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mainMenuPresenter = MainMenuPresenter(mainView, apiObject, apiRepository, gson, context, TestContextProvider())
        detailPresenter = DetailPresenter(detailView, apiRepository, apiObject, gson, TestContextProvider())
    }
    @Test
    fun getLastMatchTest(){
        val match: MutableList<MatchItem> = mutableListOf()
        val response = MatchResponse(match)

        `when`(gson.fromJson(apiRepository
                .doRequest(apiObject.getLastMatch()),
                MatchResponse::class.java)).thenReturn(response)

        mainMenuPresenter.getLastMatch()
        verify(mainView).showLoading()
        verify(mainView).hideLoading()
        verify(mainView).showLastEvent(match)
    }
    

    @Test
    fun getTeamDetailTest(){
        val team: MutableList<TeamsItem> = mutableListOf()
        val response = TeamsResponse(team)

        `when`(gson.fromJson(apiRepository
                .doRequest(apiObject.getLookupTeam("133604")),
                TeamsResponse::class.java)).thenReturn(response)

        detailPresenter.getLookupTeam("133604", "HOME")
    }
}