package com.yonjar.futbolapp.leagues.data.models.matchesModel

import com.google.gson.annotations.SerializedName
import com.yonjar.futbolapp.leagues.data.models.teamsModel.TeamModelResponse
import com.yonjar.futbolapp.leagues.domain.models.MatchModel

data class MatchModelResponse(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String?,
    @SerializedName("starting_at") val startingAt:String?,
    @SerializedName("result_info") val result:String?,
    @SerializedName("participants") val teams:List<TeamModelResponse>?,
    @SerializedName("scores") val scores:List<ScoresResponseModel>?
){
    fun toMatchModel():MatchModel{
        println(teams)
       return MatchModel(
           id = id,
           name = name ?: "",
           date = startingAt ?: "",
           result = result,
           teamHome = teams?.get(0)?.toTeamModel(),
           teamAway = teams?.get(1)?.toTeamModel(),
           goalsHome = scores?.get(scores.size - 2)?.score?.goals,
           goalsAway = scores?.get(scores.size - 1)?.score?.goals
       )
    }
}
