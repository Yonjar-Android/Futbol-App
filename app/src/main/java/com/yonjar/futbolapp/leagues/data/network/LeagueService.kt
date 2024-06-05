package com.yonjar.futbolapp.leagues.data.network

import com.yonjar.futbolapp.leagues.data.models.leagueModel.LeagueResponse
import com.yonjar.futbolapp.leagues.data.models.leagueModel.OneLeagueResponse
import com.yonjar.futbolapp.leagues.data.models.teamsModel.StandingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LeagueService {

    @GET("leagues")
    suspend fun getLeagues(@Query("include") include: String = "currentSeason"): LeagueResponse

    @GET("leagues/{id}")
    suspend fun getLeagueById(
        @Path("id") id: Int,
        @Query("include") include: String = "currentSeason"
    ): OneLeagueResponse

    @GET("standings/seasons/{id}?")
    suspend fun getStandingsBySeasonId(
        @Path("id") id: Int?,
        @Query("include") include: String = "participant;stage;group;details.type"
    ): StandingResponse


}