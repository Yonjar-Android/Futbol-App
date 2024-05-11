package com.yonjar.futbolapp.leagues.data.network

import com.yonjar.futbolapp.leagues.data.models.LeagueResponse
import com.yonjar.futbolapp.leagues.data.models.OneLeagueResponse
import com.yonjar.futbolapp.leagues.data.models.StandingResponse
import com.yonjar.futbolapp.leagues.data.models.TeamResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface LeagueService {

    @GET("leagues")
    suspend fun getLeagues(): LeagueResponse

    @GET("leagues/{id}")
    suspend fun getLeagueById(@Path("id") id:Int):OneLeagueResponse

    @GET("standings/seasons/19686")
    suspend fun getStandingsBySeasonId():StandingResponse

    @GET("teams/{id}")
    suspend fun getTeamById(@Path("id") id:Int): TeamResponse

}