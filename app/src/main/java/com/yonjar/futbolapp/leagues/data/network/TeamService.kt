package com.yonjar.futbolapp.leagues.data.network

import com.yonjar.futbolapp.leagues.data.models.teamsModel.PlayerData
import com.yonjar.futbolapp.leagues.data.models.teamsModel.PlayerModelResponse
import com.yonjar.futbolapp.leagues.data.models.teamsModel.TeamResponse
import com.yonjar.futbolapp.leagues.data.models.teamsModel.TeamSquadResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TeamService {
    @GET("teams/{id}")
    suspend fun getTeamById(
        @Path("id") id: Int,
        @Query("include") includeTeam: String = "country;venue;activeSeasons")
    : TeamResponse

    @GET("squads/teams/{id}")
    suspend fun getTeamSquadById(
        @Path("id") id: Int,
        @Query("include") includePlayer: String = "player;detailedposition;position")
    : TeamSquadResponse

    @GET("players/{id}")
    suspend fun getPlayerById(
        @Path("id") id:Int,
        @Query("include") includePlayer: String = "nationality;detailedposition;position")
    :PlayerData
}