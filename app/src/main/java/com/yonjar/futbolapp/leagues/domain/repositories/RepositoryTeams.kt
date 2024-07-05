package com.yonjar.futbolapp.leagues.domain.repositories

import com.yonjar.futbolapp.leagues.domain.models.MatchModel
import com.yonjar.futbolapp.leagues.domain.models.PlayerModel
import com.yonjar.futbolapp.leagues.domain.models.PlayerStatistics
import com.yonjar.futbolapp.leagues.domain.models.teamModels.TeamModel
import com.yonjar.futbolapp.leagues.domain.models.teamModels.TeamSquadModel

interface RepositoryTeams {

    suspend fun getTeamById(id:Int): TeamModel?

    suspend fun getTeamSquadById(id:Int):List<TeamSquadModel>?

    suspend fun getPlayerById(id: Int):PlayerModel?

    suspend fun getPlayerStatistics(id:Int, seasonId:Int):PlayerStatistics?

    suspend fun getMatchesByTeamId(id:Int, include:String):List<MatchModel>?

}