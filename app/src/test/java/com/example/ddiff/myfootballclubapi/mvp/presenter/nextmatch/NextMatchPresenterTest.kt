package com.example.ddiff.myfootballclubapi.mvp.presenter.nextmatch

import com.example.ddiff.myfootballclubapi.mvp.model.MatchModel
import com.example.ddiff.myfootballclubapi.mvp.model.MatchResponse
import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.SchedulerProvider
import com.example.ddiff.myfootballclubapi.utils.TestSchedulerProvider
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
/**
 * Created by Diffa Dwi Desyawan on 23/9/18.
 * email : diffadwi1@gmail.com.
 * github : ddiffa
 */
class NextMatchPresenterTest {

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var matchView: NextMatchContract.View
    lateinit var presenter: NextMatchPresenter

    lateinit var matchResponse: MatchResponse

    lateinit var match: Observable<MatchResponse>

    private val events = mutableListOf<MatchModel>()
    lateinit var scheduler: SchedulerProvider

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        scheduler = TestSchedulerProvider()
        matchResponse = MatchResponse(events)
        match = Observable.just(matchResponse)
        presenter = NextMatchPresenter(matchView, apiService, scheduler)
        Mockito.`when`(apiService.loadNextMatch()).thenReturn(match)
    }

    @Test
    fun getNextMatch() {
        presenter.getNextMatch()
        Mockito.verify(matchView).showProgress()
        Mockito.verify(matchView).onLoadClub(events)
        Mockito.verify(matchView).hideProgress()
    }
}