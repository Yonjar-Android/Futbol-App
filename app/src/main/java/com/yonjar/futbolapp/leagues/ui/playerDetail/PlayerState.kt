package com.yonjar.futbolapp.leagues.ui.playerDetail

import com.yonjar.futbolapp.leagues.domain.models.PlayerModel

sealed class PlayerState {
    data object Loading:PlayerState()
    data class Success(val player: PlayerModel): PlayerState()
    data class Error(val errorMessage:String): PlayerState()
}