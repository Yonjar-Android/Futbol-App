package com.yonjar.futbolapp.leagues.domain.repositories

import com.yonjar.futbolapp.leagues.domain.models.MatchModel
import com.yonjar.futbolapp.leagues.domain.models.leagueModels.LeagueModel
import com.yonjar.futbolapp.leagues.domain.models.leagueModels.StandingModel


interface RepositoryLeagues {
    suspend fun getLeagues():List<LeagueModel>?

    suspend fun getLeagueById(id:Int): LeagueModel?

    suspend fun getStandingBySeasonId(id:Int?):List<StandingModel>?

    suspend fun getStandingPlayOffOneBySeasonId(id:Int?):List<StandingModel>?

    suspend fun getMatchesByLeagueId(id:Int?, include:String):List<MatchModel>?

}