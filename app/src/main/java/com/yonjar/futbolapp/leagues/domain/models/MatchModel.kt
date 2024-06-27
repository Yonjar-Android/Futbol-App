package com.yonjar.futbolapp.leagues.domain.models

import com.yonjar.futbolapp.leagues.domain.models.teamModels.TeamModel

data class MatchModel(
    val id:Int?,
    val name:String,
    val date:String,
    val result:String?,
    val teamHome:TeamModel?,
    val teamAway:TeamModel?,
    val goalsHome:Int? = 0,
    val goalsAway:Int? = 0,
    val goalMinutes:List<EventModel>?
)
