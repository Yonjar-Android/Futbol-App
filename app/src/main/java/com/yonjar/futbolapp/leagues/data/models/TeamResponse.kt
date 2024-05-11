package com.yonjar.futbolapp.leagues.data.models

import com.google.gson.annotations.SerializedName

data class TeamResponse(
    @SerializedName("data") val data:TeamModel
)

data class TeamModel(
    @SerializedName("id") val id:Int,
    @SerializedName("country_id") val countryId:Int,
    @SerializedName("name") val name:String,
    @SerializedName("short_code") val shortName:String,
    @SerializedName("image_path") val teamImage:String,
    @SerializedName("founded") val yearFounded:Int
)

data class TeamSquadResponse(
    @SerializedName("data") val data:TeamModel
)

data class TeamSquadModel(
    @SerializedName("id") val id:Int,
    @SerializedName("player_id") val playerId:Int,
    @SerializedName("team_id") val teamId:Int,
    @SerializedName("jersey_number") val jerseyNumber:Int
)