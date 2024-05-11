package com.yonjar.futbolapp.leagues.domain.models

data class TeamSquadModel(
    val id: Int,
    val playerId: Int?,
    val teamId: Int?,
    val jerseyNumber: Int?
)
