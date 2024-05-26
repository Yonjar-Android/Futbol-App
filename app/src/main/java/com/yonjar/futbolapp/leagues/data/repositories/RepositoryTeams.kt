package com.yonjar.futbolapp.leagues.data.repositories

import android.util.Log
import com.yonjar.futbolapp.leagues.data.network.TeamService
import com.yonjar.futbolapp.leagues.domain.models.TeamModel
import com.yonjar.futbolapp.leagues.domain.models.TeamSquadModel
import com.yonjar.futbolapp.leagues.domain.repositories.RepositoryTeams
import javax.inject.Inject

class RepositoryTeams @Inject constructor
    (private val teamService: TeamService)
    : RepositoryTeams {
    override suspend fun getTeamById(id: Int): TeamModel? {
        runCatching {
            teamService.getTeamById(id)
        }.onSuccess { return it.data.toTeamModel() }
            .onFailure { Log.i("Error Message", "Error: ${it.message}") }

        return null
    }

    override suspend fun getTeamSquadById(id: Int): List<TeamSquadModel>? {
        runCatching {
            teamService.getTeamSquadById(id)
        }.onSuccess {
            val squadList = mutableListOf<TeamSquadModel>()

            for(n in it.data){
                squadList.add(n.toTeamSquadModel())
            }

            return squadList

        }.onFailure {
            Log.i("Error Message", "Error: ${it.message}")
        }
        return null
    }
}