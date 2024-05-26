package com.yonjar.futbolapp.leagues.domain.models

data class PlayerModel(
    val playerId:Int,
    val transferId:Int?,
    val captain:Boolean?,
    val jerseyNumber:Int?,
    val nationalityId:Int?,
    val positionId:Int?,
    val name:String?,
    val playerImage:String?,
    val height:Int?,
    val weight:Int?,
    val gender:String?,
    val position:String?,
    val detailedPosition:String?
)
