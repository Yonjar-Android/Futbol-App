package com.yonjar.futbolapp.leagues.ui.leagueMainScreen

import com.yonjar.futbolapp.leagues.data.models.LeagueModel

sealed class LeaguesState {
    data object Loading: LeaguesState()
    data class Success(val leagues: List<LeagueModel>?): LeaguesState()
    data class Error(val errorMessage:String): LeaguesState()
}