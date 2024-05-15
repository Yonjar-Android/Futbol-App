package com.yonjar.futbolapp.leagues.domain.models

data class CurrentSeasonModel(
    val idSeason:Int?,
    val name:String?,
    val finished:Boolean?,
    val isCurrent:Boolean?,
    val startingAt:String?,
    val endingAt:String?
)