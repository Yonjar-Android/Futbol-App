package com.yonjar.futbolapp.leagues.data

import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import com.yonjar.futbolapp.leagues.data.network.LeagueService
import com.yonjar.futbolapp.leagues.domain.models.LeagueModel
import com.yonjar.futbolapp.leagues.domain.models.StandingModel
import com.yonjar.futbolapp.leagues.domain.models.TeamModel
import com.yonjar.futbolapp.leagues.domain.repositories.Repository
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.system.measureTimeMillis

@Singleton
class Repository @Inject constructor(private val leagueService: LeagueService): Repository {

    override suspend fun getLeagues():List<LeagueModel>?{
        runCatching {
            leagueService.getLeagues()
        }.onSuccess {
            val leaguesList = mutableListOf<LeagueModel>()

            for (n in it.leagues){
                if(n.subType == "domestic"){
                    leaguesList.add(n.toLeagueModel())
                }

            }

            return leaguesList

        }
            .onFailure { Log.i("Error", "Error: $it") }
        return null
    }

    override suspend fun getLeagueById(id:Int):LeagueModel?{
        runCatching {
            leagueService.getLeagueById(id)
        }.onSuccess {
            Log.i("league",it.league.toLeagueModel().toString())
            return it.league.toLeagueModel() }
            .onFailure { Log.i("ErrorOnFailure", "Error ${it.message}") }
        return null
    }

    override suspend fun getStandingBySeasonId(id:Int?):List<StandingModel>?{
        runCatching {
            leagueService.getStandingsBySeasonId(id)
        }.onSuccess { standings ->
            val newList = mutableListOf<StandingModel>()
            for (s in standings.data){
                if(s.stage?.name == "Regular Season" || s.stage?.name == "1st Phase"){
                    newList.add(s.toStandingModel())
                }
            }
            return newList
        }
        .onFailure { Log.i("Error Message","Error: ${it.message}") }
        return null
    }

    override suspend fun getTeamById(id:Int): TeamModel?{
       runCatching {
           leagueService.getTeamById(id)
       } .onSuccess { return it.data.toTeamModel() }
           .onFailure { Log.i("Error Message", "Error: ${it.message}") }

        return null
    }
}