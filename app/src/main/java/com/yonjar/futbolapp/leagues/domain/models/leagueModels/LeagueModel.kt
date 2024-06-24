package com.yonjar.futbolapp.leagues.domain.models.leagueModels

import com.yonjar.futbolapp.leagues.domain.models.CurrentSeasonModel
import com.yonjar.futbolapp.leagues.domain.models.MatchModel

data class LeagueModel(
    val id: Int,
    val countryId: Int,
    val name: String,
    val leagueImage: String,
    val subType:String,
    val currentSeason: CurrentSeasonModel?,
    val upcomingMatches:List<MatchModel>?
)