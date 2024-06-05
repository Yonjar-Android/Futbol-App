package com.yonjar.futbolapp.leagues.domain.models

data class StandingModel(
    val id: Int,
    val participantId: Int?,
    val sportId: Int?,
    val leagueId: Int?,
    val seasonId: Int?,
    val stageId: Long?,
    val groupId: Int?,
    val roundId: Int?,
    val standingRuleId: Int?,
    val position: Int?,
    val result: String?,
    val points: Int?,
    val participant:TeamModel,
    val stage:StageModel?,
    val group:GroupModel?,
    val details:StandingDetailModel
)