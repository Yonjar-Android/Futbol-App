package com.yonjar.futbolapp.leagues.ui.leagueDetail

import com.yonjar.futbolapp.leagues.data.models.LeagueModel
import com.yonjar.futbolapp.leagues.data.models.StandingModel

sealed class DetailLeagueState {

    data object Loading:DetailLeagueState()

    data class Error(val errorMessage:String):DetailLeagueState()

    data class Success(val league: LeagueModel, val response2: StandingModel):DetailLeagueState()

}