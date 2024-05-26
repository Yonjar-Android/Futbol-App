package com.yonjar.futbolapp.leagues.domain.repositories

import com.yonjar.futbolapp.leagues.domain.models.TeamModel
import com.yonjar.futbolapp.leagues.domain.models.TeamSquadModel

interface RepositoryTeams {

    suspend fun getTeamById(id:Int): TeamModel?

    suspend fun getTeamSquadById(id:Int):List<TeamSquadModel>?

}