package com.yonjar.futbolapp.leagues.data.models

import com.google.gson.annotations.SerializedName
import com.yonjar.futbolapp.leagues.domain.models.StandingModel

data class StandingResponse(
@SerializedName("data") val data:List<StandingModelResponse>
)

data class StandingModelResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("participant_id") val participantId: Int,
    @SerializedName("sport_id") val sportId: Int,
    @SerializedName("league_id") val leagueId: Int,
    @SerializedName("season_id") val seasonId: Int,
    @SerializedName("stage_id") val stageId: Long,
    @SerializedName("group_id") val groupId: Int?,
    @SerializedName("round_id") val roundId: Int,
    @SerializedName("standing_rule_id") val standingRuleId: Int,
    @SerializedName("position") val position: Int,
    @SerializedName("result") val result: String,
    @SerializedName("points") val points: Int
){
    fun toStandingModel(name:String, teamShield:String) = StandingModel(
        id = id,
        participantId = participantId,
        sportId = sportId,
        leagueId = leagueId,
        seasonId = seasonId,
        stageId = stageId,
        groupId = groupId,
        roundId = roundId,
        standingRuleId = standingRuleId,
        position = position,
        result = result,
        points = points,
        name = name,
        teamImage = teamShield

    )
}