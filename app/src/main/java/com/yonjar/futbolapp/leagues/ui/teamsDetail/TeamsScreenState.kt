package com.yonjar.futbolapp.leagues.ui.teamsDetail

import com.yonjar.futbolapp.leagues.domain.models.teamModels.TeamModel
import com.yonjar.futbolapp.leagues.domain.models.teamModels.TeamSquadModel

sealed class TeamsScreenState {
    data object Loading: TeamsScreenState()
    data class Error(val errorMessage:String): TeamsScreenState()
    data class Success(val team: TeamModel, val squadList:List<TeamSquadModel>):TeamsScreenState()
}