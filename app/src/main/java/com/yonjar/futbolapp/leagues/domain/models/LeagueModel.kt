package com.yonjar.futbolapp.leagues.domain.models

data class LeagueModel(
    val id: Int,
    val countryId: Int,
    val name: String,
    val leagueImage: String,
    val subType:String,
    val currentSeason:CurrentSeasonModel?
)