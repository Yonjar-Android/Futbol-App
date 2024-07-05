package com.yonjar.futbolapp.leagues.domain.models.teamModels

import com.yonjar.futbolapp.leagues.domain.models.MatchModel


data class TeamModel(
    val id: Int,
    val countryId: Int,
    val name: String?,
    val shortName: String?,
    val teamImage: String?,
    val yearFounded: Int?,
    val countryName:String?,
    val countryFlag:String?,
    val cityName:String?,
    val stadiumName:String?,
    val stadiumImage:String?,
    val currentSeasonId:Int?,
    val currentSeasonName:String?,
    val nextMatches:List<MatchModel>?,
    val lastMatches:List<MatchModel>?

)