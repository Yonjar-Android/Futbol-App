package com.yonjar.futbolapp.leagues.data

import android.util.Log
import com.yonjar.futbolapp.leagues.data.network.LeagueService
import com.yonjar.futbolapp.leagues.domain.models.LeagueModel
import com.yonjar.futbolapp.leagues.domain.models.StandingModel
import com.yonjar.futbolapp.leagues.domain.models.TeamModel
import com.yonjar.futbolapp.leagues.domain.repositories.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val leagueService: LeagueService): Repository {

    override suspend fun getLeagues():List<LeagueModel>?{
        runCatching {
            leagueService.getLeagues()
        }.onSuccess {
            return it.leagues.map {league-> league.toLeagueModel() }
        }
            .onFailure { Log.i("Error", "Error: $it") }
        return null
    }

    override suspend fun getLeagueById(id:Int):LeagueModel?{
        runCatching {
            leagueService.getLeagueById(id)
        }.onSuccess { return it.league.toLeagueModel() }
            .onFailure { Log.i("ErrorOnFailure", "Error ${it.message}") }
        return null
    }

    override suspend fun getStandingBySeasonId(id:Int):List<StandingModel>?{
        runCatching {
            leagueService.getStandingsBySeasonId()
        }.onSuccess { standings ->

            val teams = mutableListOf<StandingModel>()
            val teamsAdded = mutableListOf<Int>()


            for (s in standings.data){
                if(s.participantId !in teamsAdded){
                    teamsAdded.add(s.participantId)

                    val teamCall = getTeamById(s.participantId)

                    if (teamCall != null){
                        teams.add(s.toStandingModel(teamCall.name ?: "", teamCall.teamImage ?: ""))
                    }
                }

            }
            return teams

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