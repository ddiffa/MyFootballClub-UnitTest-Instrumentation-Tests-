package com.example.ddiff.myfootballclubapi.network

import com.example.ddiff.myfootballclubapi.mvp.model.MatchResponse
import com.example.ddiff.myfootballclubapi.mvp.model.TeamResponse
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Diffa Dwi Desyawan on 15/9/18.
 * email : diffadwi1@gmail.com.
 */

class ApiService {
    val service: ApiServiceInterface

    var baseUrl = "https://www.thesportsdb.com/api/v1/json/1/"

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val gson = GsonBuilder()
                .setLenient()
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()

        service = retrofit.create<ApiServiceInterface>(ApiServiceInterface::class.java)
    }

    fun loadMatch(): Observable<MatchResponse> {
        return service.getLastMatch()
    }

    fun loadNextMatch(): Observable<MatchResponse> {
        return service.getNextMatch()
    }

    fun loadTeam(id: String?): Observable<TeamResponse> {
        return service.getTeamInfo(id!!)
    }
}