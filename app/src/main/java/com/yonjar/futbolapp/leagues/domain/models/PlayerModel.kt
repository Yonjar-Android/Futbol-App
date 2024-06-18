package com.yonjar.futbolapp.leagues.domain.models

data class PlayerModel(
    val playerId:Int,
    val transferId:Int?,
    val captain:Boolean?,
    val jerseyNumber:Int?,
    val nationality:String?,
    val countryFlag:String?,
    val positionId:Int?,
    val name:String?,
    val playerImage:String?,
    val height:Int?,
    val weight:Int?,
    val gender:String?,
    val position:String?,
    val detailedPosition:String?,
    val statistics:List<PlayerStatistics>?,
    val dateOfBirth:String?
)

data class PlayerStatistics(
    val statisticList: List<PlayerStatisticDetail>
)

data class PlayerStatisticDetail(
    val statisticName:String?,
    val statisticTotal:Int?,
)