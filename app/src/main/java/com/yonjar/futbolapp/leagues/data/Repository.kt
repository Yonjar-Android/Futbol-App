package com.yonjar.futbolapp.leagues.data

import android.util.Log
import com.yonjar.futbolapp.leagues.data.models.LeagueModel
import com.yonjar.futbolapp.leagues.data.models.StandingModel
import com.yonjar.futbolapp.leagues.data.models.TeamModel
import com.yonjar.futbolapp.leagues.data.network.LeagueService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val leagueService: LeagueService) {

    suspend fun getLeagues():List<LeagueModel>?{
        runCatching {
            leagueService.getLeagues()
        }.onSuccess {
            return it.leagues }
            .onFailure { Log.i("Error", "Error: $it") }
        return null
    }

    suspend fun getLeagueById(id:Int):LeagueModel?{
        runCatching {
            leagueService.getLeagueById(id)
        }.onSuccess { return it.league }
            .onFailure { Log.i("ErrorOnFailure", "Error ${it.message}") }
        return null
    }

    suspend fun getStandingBySeasonId(id:Int):StandingModel?{
        runCatching {
            leagueService.getStandingsBySeasonId()
        }.onSuccess {
            return it.data[0]
        }.onFailure { Log.i("Error Message","Error: ${it.message}") }
        return null
    }

    suspend fun getTeamById(id:Int):TeamModel?{
       runCatching {
           leagueService.getTeamById(id)
       } .onSuccess { return it.data }
           .onFailure { Log.i("Error Message", "Error: ${it.message}") }

        return null
    }
}