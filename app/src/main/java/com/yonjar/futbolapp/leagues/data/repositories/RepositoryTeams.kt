package com.yonjar.futbolapp.leagues.data.repositories

import com.yonjar.futbolapp.leagues.data.network.TeamService
import com.yonjar.futbolapp.leagues.domain.models.PlayerModel
import com.yonjar.futbolapp.leagues.domain.models.PlayerStatistics
import com.yonjar.futbolapp.leagues.domain.models.teamModels.TeamModel
import com.yonjar.futbolapp.leagues.domain.models.teamModels.TeamSquadModel
import com.yonjar.futbolapp.leagues.domain.repositories.RepositoryTeams
import javax.inject.Inject

class RepositoryTeams @Inject constructor
    (private val teamService: TeamService) : RepositoryTeams {

    override suspend fun getTeamById(id: Int): TeamModel? {
        runCatching {
            teamService.getTeamById(id)
        }.onSuccess {
            return it.data.toTeamModel()
        }
            .onFailure { println("Error: ${it.message}") }

        return null
    }

    override suspend fun getTeamSquadById(id: Int): List<TeamSquadModel>? {
        runCatching {
            teamService.getTeamSquadById(id)
        }.onSuccess {
            val squadList = mutableListOf<TeamSquadModel>()

            for (n in it.data) {
                squadList.add(n.toTeamSquadModel())
            }

            return squadList

        }.onFailure {
            println("Error: ${it.message}")
        }
        return null
    }

    override suspend fun getPlayerById(id: Int): PlayerModel? {
        runCatching {
            teamService.getPlayerById(id)
        }.onSuccess {
            return it.player.toPlayerModel()
        }.onFailure {
            println("Error: ${it.message}")
        }
        return null
    }

    override suspend fun getPlayerStatistics(id: Int, seasonId: Int): PlayerStatistics? {
        runCatching {
            teamService.getPlayerById(id, "statistics.details.type")
        }.onSuccess {
            if (it.player.statistics?.isNotEmpty() == true) {
                for (n in it.player.statistics) {
                    if (n.seasonId == seasonId) {
                        return n.toPlayerStatistic()
                    }
                    continue
                }
            }
            return null
        }.onFailure {
            println("Error: ${it.message}")
        }
        return null
    }
}