package com.yonjar.futbolapp.leagues.domain.repositories

import com.yonjar.futbolapp.leagues.domain.models.LeagueModel
import com.yonjar.futbolapp.leagues.domain.models.StandingModel


interface RepositoryLeagues {
    suspend fun getLeagues():List<LeagueModel>?

    suspend fun getLeagueById(id:Int):LeagueModel?

    suspend fun getStandingBySeasonId(id:Int?):List<StandingModel>?

    suspend fun getStandingPlayOffOneBySeasonId(id:Int?):List<StandingModel>?

}