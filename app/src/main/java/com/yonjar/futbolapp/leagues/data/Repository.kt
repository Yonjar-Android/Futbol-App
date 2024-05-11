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
        }.onSuccess { league ->
            val teamStandings = mutableListOf<StandingModel>()
            val addedParticipantIds = HashSet<Int>() // Conjunto para mantener un seguimiento de los participantId ya agregados
            league.data.mapNotNull { standing ->
                val team = getTeamById(standing.participantId)
                team?.name?.let { teamName ->
                    team.teamImage?.let { teamImage ->
                        standing.toStandingModel(teamName, teamImage)
                    }
                }
            }.forEach { standingModel ->
                // Verificar si el participantId ya está agregado antes de agregarlo a teamStandings
                if (standingModel.participantId !in addedParticipantIds) {
                    teamStandings.add(standingModel)
                    standingModel.participantId?.let { addedParticipantIds.add(it) } // Agregar el participantId al conjunto
                }
            }
            return teamStandings
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