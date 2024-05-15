package com.yonjar.futbolapp.leagues.domain.repositories

import com.yonjar.futbolapp.leagues.domain.models.LeagueModel
import com.yonjar.futbolapp.leagues.domain.models.StandingModel
import com.yonjar.futbolapp.leagues.domain.models.TeamModel


interface Repository {
    suspend fun getLeagues():List<LeagueModel>?

    suspend fun getLeagueById(id:Int):LeagueModel?

    suspend fun getStandingBySeasonId(id:Int?):List<StandingModel>?
    suspend fun getTeamById(id:Int): TeamModel?

}