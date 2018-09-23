package com.example.ddiff.myfootballclubapi.network

import com.example.ddiff.myfootballclubapi.mvp.model.MatchResponse
import com.example.ddiff.myfootballclubapi.mvp.model.TeamResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
/**
 * Created by Diffa Dwi Desyawan on 15/9/18.
 * email : diffadwi1@gmail.com.
 */
interface ApiServiceInterface {

    @GET("eventsnextleague.php?id=4328")
    fun getNextMatch(): Observable<MatchResponse>

    @GET("eventspastleague.php?id=4328")
    fun getLastMatch(): Observable<MatchResponse>

    @GET("lookupteam.php")
    fun getTeamInfo(@Query("id") id: String): Observable<TeamResponse>
}