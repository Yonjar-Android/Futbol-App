package com.yonjar.futbolapp.leagues.data.models

import com.google.gson.annotations.SerializedName
import com.yonjar.futbolapp.leagues.domain.models.CurrentSeasonModel

data class CurrentSeasonModelResponse(
    @SerializedName("id") val idSeason:Int,
    @SerializedName("name") val name:String,
    @SerializedName("finished") val finished:Boolean,
    @SerializedName("is_current") val isCurrent:Boolean,
    @SerializedName("starting_at") val startingAt:String,
    @SerializedName("ending_at") val endingAt:String
){
    fun toCurrentSeasonModel():CurrentSeasonModel =
        CurrentSeasonModel(
            idSeason = idSeason,
            name = name,
            finished = finished,
            isCurrent = isCurrent,
            startingAt = startingAt,
            endingAt = endingAt
        )
}
