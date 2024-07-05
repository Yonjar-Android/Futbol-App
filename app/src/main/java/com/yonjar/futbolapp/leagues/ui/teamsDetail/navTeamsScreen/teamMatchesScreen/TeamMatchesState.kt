package com.yonjar.futbolapp.leagues.ui.teamsDetail.navTeamsScreen.teamMatchesScreen

import com.yonjar.futbolapp.leagues.domain.models.MatchModel

sealed class TeamMatchesState {

    data object Loading:TeamMatchesState()

    data class Error(val errorMessage:String):TeamMatchesState()

    data class Success(val matches:List<MatchModel>):TeamMatchesState()
}