package com.yonjar.futbolapp.leagues.domain.models.teamModels

import com.yonjar.futbolapp.leagues.domain.models.PlayerModel

data class TeamSquadModel(
    val id: Int,
    val playerId: Int?,
    val teamId: Int?,
    val jerseyNumber: Int?,
    val player: PlayerModel
)
