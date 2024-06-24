package com.yonjar.futbolapp.leagues.data.models.teamsModel

import com.google.gson.annotations.SerializedName
import com.yonjar.futbolapp.leagues.data.models.leagueModel.GroupResponse
import com.yonjar.futbolapp.leagues.data.models.leagueModel.StageModelResponse
import com.yonjar.futbolapp.leagues.data.models.teamsModel.standingDetails.StandingDetails
import com.yonjar.futbolapp.leagues.domain.models.teamModels.StandingDetailModel
import com.yonjar.futbolapp.leagues.domain.models.leagueModels.StandingModel

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
    @SerializedName("points") val points: Int,
    @SerializedName("participant") val team: TeamModelResponse,
    @SerializedName("stage") val stage: StageModelResponse?,
    @SerializedName("group") val group: GroupResponse?,
    @SerializedName("details") val details:List<StandingDetails>?
){
    fun toStandingModel(): StandingModel {

        var standingModel = StandingDetailModel(0,0,0)

        if(details?.isNotEmpty() == true){
                standingModel = StandingDetailModel(
                        wins = details.find { it.type.developerName == "OVERALL_WINS" }?.value ?: 0,
                        losses = details.find { it.type.developerName == "OVERALL_LOST" }?.value ?: 0,
                        draws = details.find { it.type.developerName == "OVERALL_DRAWS" }?.value ?: 0
                    )
            }

        return StandingModel(
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
            participant = team.toTeamModel(),
            stage = stage?.toStageModel(),
            group = group?.toGroupModel(),
            details = standingModel
        )
    }


}
