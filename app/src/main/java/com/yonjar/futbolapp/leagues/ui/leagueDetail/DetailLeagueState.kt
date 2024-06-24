package com.yonjar.futbolapp.leagues.ui.leagueDetail

import com.yonjar.futbolapp.leagues.domain.models.leagueModels.LeagueModel
import com.yonjar.futbolapp.leagues.domain.models.leagueModels.StandingModel

sealed class DetailLeagueState {

    data object Loading:DetailLeagueState()

    data class Error(val errorMessage:String):DetailLeagueState()

    data class Success(val league: LeagueModel, val standings: List<StandingModel>?, val playOffStandings:List<StandingModel>?):DetailLeagueState()

}