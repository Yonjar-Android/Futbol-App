package com.yonjar.futbolapp.leagues.data.models

import com.google.gson.annotations.SerializedName

data class LeagueResponse(
    @SerializedName("data") val leagues:List<LeagueModel>
)

data class OneLeagueResponse(
    @SerializedName("data") val league:LeagueModel
)

data class LeagueModel(
    @SerializedName("id") val id:Int,
    @SerializedName("country_id") val countryId:Int,
    @SerializedName("name") val name:String,
    @SerializedName("image_path") val leagueImage:String
)

data class TeamsModel(
    @SerializedName("id") val id:Int,
    @SerializedName("sports_id") val sportsId:Int,
    @SerializedName("season_id") val seasonId:Int,
    @SerializedName("league_id") val leagueId:Int,
    @SerializedName("participant_id") val participantId:Int,
    @SerializedName("position") val position:Int,
    @SerializedName("points") val points:Int
)
