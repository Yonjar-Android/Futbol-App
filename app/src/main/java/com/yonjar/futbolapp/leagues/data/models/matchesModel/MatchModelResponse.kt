package com.yonjar.futbolapp.leagues.data.models.matchesModel

import com.google.gson.annotations.SerializedName
import com.yonjar.futbolapp.leagues.data.models.teamsModel.TeamModelResponse
import com.yonjar.futbolapp.leagues.domain.models.EventModel
import com.yonjar.futbolapp.leagues.domain.models.MatchModel

data class MatchModelResponse(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String?,
    @SerializedName("starting_at") val startingAt:String?,
    @SerializedName("result_info") val result:String?,
    @SerializedName("participants") val teams:List<TeamModelResponse>?,
    @SerializedName("scores") val scores:List<ScoresResponseModel>?,
    @SerializedName("events") val events:List<EventModelResponse>?
){
    fun toMatchModel():MatchModel{

        val goalsListMinutes = mutableListOf<EventModel>()

        if(events?.isNotEmpty() == true){
            for(n in events.indices){
                if(events[n].addition?.contains("Goal") == true
                    && events[n].addition?.contains("Disallowed") == false){
                    goalsListMinutes.add(events[n].toEventModel())
                }
            }
        }
       return MatchModel(
           id = id,
           name = name ?: "",
           date = startingAt ?: "",
           result = result,
           teamHome = teams?.get(0)?.toTeamModel(),
           teamAway = teams?.get(1)?.toTeamModel(),
           goalsHome = scores?.get(scores.size - 2)?.score?.goals,
           goalsAway = scores?.get(scores.size - 1)?.score?.goals,
           goalMinutes = goalsListMinutes.sortedBy { it.minute }
       )
    }
}
