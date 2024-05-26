package com.yonjar.futbolapp.leagues.data.network

import com.yonjar.futbolapp.leagues.data.models.TeamResponse
import com.yonjar.futbolapp.leagues.data.models.TeamSquadModelResponse
import com.yonjar.futbolapp.leagues.data.models.TeamSquadResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TeamService {
    @GET("teams/{id}")
    suspend fun getTeamById(@Path("id") id: Int): TeamResponse

    @GET("squads/teams/{id}")
    suspend fun getTeamSquadById(
        @Path("id") id: Int,
        @Query("include") includePlayer: String = "player;detailedposition;position")
    : TeamSquadResponse
}