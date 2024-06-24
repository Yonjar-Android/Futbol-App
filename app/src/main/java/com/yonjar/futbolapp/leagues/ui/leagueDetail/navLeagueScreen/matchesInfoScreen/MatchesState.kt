package com.yonjar.futbolapp.leagues.ui.leagueDetail.navLeagueScreen.matchesInfoScreen

import com.yonjar.futbolapp.leagues.domain.models.MatchModel

sealed class MatchesState {
    data object Loading:MatchesState()
    data class Error(val errorMessage:String):MatchesState()
    data class Success(val matchesState: List<MatchModel>?):MatchesState()
}