package com.yonjar.futbolapp.leagues.data.models.matchesModel

import com.google.gson.annotations.SerializedName
import com.yonjar.futbolapp.leagues.domain.models.EventModel

data class EventModelResponse(
    @SerializedName("id") val id:Int,
    @SerializedName("player_name") val playerName:String?,
    @SerializedName("addition") val addition:String?,
    @SerializedName("minute") val minute:Int?,
    @SerializedName("participant_id") val participantId:Int?
){
    fun toEventModel():EventModel{
        return EventModel(
            id = id,
            player = playerName,
            addition = addition,
            minute = minute,
            participant = participantId
        )
    }
}