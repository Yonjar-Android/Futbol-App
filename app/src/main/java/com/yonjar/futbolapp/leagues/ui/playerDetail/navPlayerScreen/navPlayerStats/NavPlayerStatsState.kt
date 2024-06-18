package com.yonjar.futbolapp.leagues.ui.playerDetail.navPlayerScreen.navPlayerStats

import com.yonjar.futbolapp.leagues.domain.models.PlayerStatistics

sealed class NavPlayerStatsState {
    data object Loading:NavPlayerStatsState()
    data class Error(val errorMessage:String):NavPlayerStatsState()
    data class Success(val list:PlayerStatistics):NavPlayerStatsState()
}